package messageHandler;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.AgentManager;
import agents.ResearchAgent;
import dataManager.RealEstateDataBean;
import model.ACLMessage;
import model.AID;
import model.Performative;
import realEstate.RealEstate;
import realEstate.RealEstateDTO;
import rest.ResearchAgentRest;
import rest.ResearchAgentRestRemote;

@LocalBean
@Remote(ACLMessageHandlerInterface.class)
@Stateless
public class ACLMessageHandler implements ACLMessageHandlerInterface{

	
	@EJB
	private AgentManager agents;
	
	@EJB
	private RealEstateDataBean dataBean;
	
	
	@Override
	public void handlePerformative(ACLMessage message) {
		// TODO Auto-generated method stub
		ResearchAgent sender = agents.getResearchAgentByName(message.getSender().getName());
		if(sender != null) {
			System.out.println("Uso u mdb petlju");
			sender.handleMessage(message);
		}
		for(AID a : message.getReceivers()) {
			ResearchAgent receiver = agents.getResearchAgentByName(a.getName());
			receiver.handleMessage(message);
		}
	}


	@Override
	public String testJNDI() {
		// TODO Auto-generated method stub
		return "Radi dobro LOOKUP";
	}


	@Override
	public void handleRequest(ACLMessage message) {
		ResearchAgent sender = this.agents.getResearchAgentByName(message.getSender().getName());
		if(sender.getAid() != null) {
		sender.handleMessage(message);
		
		ObjectMapper mapper = new ObjectMapper();
		RealEstateDTO dto = new RealEstateDTO();
		
		try {
			dto = mapper.readValue(message.getContent(), RealEstateDTO.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<RealEstate> retList = new ArrayList<>();
		retList = dataBean.filter(dto);
		
		dataBean.getFilteredRealEstate().addAll(retList);
		
		}
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		
		for(AID a : message.getReceivers()) {
			ResearchAgent receiver = this.agents.getResearchAgentByName(a.getName());
			//System.out.println(receiver.getAid().getName() + "  Ime risivera;");
			//System.out.println(receiver.getAid().getHost() + "  Host;");
			//System.out.println(receiver.getAid().getHost().getAddress() + "  Adresa");
			
			if(receiver.getAid() != null) {  
				receiver.handleMessage(message);  ///dodaj agentu primacu poruku
				System.out.println("USao u petlju da risiver nije null");
				
				
				ObjectMapper mapper = new ObjectMapper();
				RealEstateDTO dto = new RealEstateDTO();
				
				try {
					dto = mapper.readValue(message.getContent(), RealEstateDTO.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<RealEstate> retList = new ArrayList<>();
				retList = dataBean.filter(dto);
				
				AID[] newReceivers = new AID[1];
				newReceivers[0] = message.getSender();
				
				ACLMessage returnMessage = new ACLMessage();
				
				returnMessage.setSender(receiver.getAid());
				returnMessage.setReceivers(newReceivers);
				
				if(retList.size() != 0) {
					returnMessage.setPerformative(Performative.inform);
					try {
						String content = mapper.writeValueAsString(retList);
						returnMessage.setContent(content);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					returnMessage.setPerformative(Performative.failure);
					returnMessage.setContent("");
				}
				
				ResteasyClient client1 = new ResteasyClientBuilder().build();
				ResteasyWebTarget target1 = client1.target("http://" + sender.getAid().getHost().getAlias() + "/project-war/rest/agentRest");
				ResearchAgentRestRemote ragent1 = target1.proxy(ResearchAgentRestRemote.class);
				ragent1.sendACLMessage(returnMessage);
				
				ResteasyWebTarget target = client.target("http://" + receiver.getAid().getHost().getAlias()+ "/project-war/rest/agentRest");
				ResearchAgentRestRemote ragent = target1.proxy(ResearchAgentRestRemote.class);
				ragent.sendACLMessage(returnMessage);
				
				
				///linije koda iznad opisuju vracanje poruke posiljaocu koja sadrzi filtrirane liste, redoslijed pozivanja je bitan
				///jer ako prvo posaljemo korisniku masine na kojoj se filer radi, nece uci u naredni poziv ResteasyClient-a 
				///nego ce MDB da reaguje i krene ispocetka
				
				
				
			}else {
			ResteasyWebTarget target = client.target("http://" + a.getHost().getAddress() + "/project-war/rest/agentRest");
			ResearchAgentRestRemote ragent = target.proxy(ResearchAgentRestRemote.class);
			System.out.println("Ime risivera :" + a.getName());
			System.out.println(a.getHost().getAddress());
			System.out.println(a.getHost().getAlias());
			
			ragent.sendACLMessage(message);
			}
		}
		
		
	}


	@Override
	public void handleInform(ACLMessage ACLMessage) {
		// TODO Auto-generated method stub
		System.out.println("USAO U INFORM PETLJU!!!");
		
		ResearchAgent sender = this.agents.getResearchAgentByName(ACLMessage.getSender().getName());
		if(sender.getAid() != null) {
			
			sender.handleMessage(ACLMessage);
			
		}
		
		
		
		for(AID a : ACLMessage.getReceivers()) {
			ResearchAgent receiver = this.agents.getResearchAgentByName(a.getName());
			System.out.println("ime primaca je " + receiver.getAid().getName());
			if(receiver.getAid() != null) {
				receiver.handleMessage(ACLMessage);
				System.out.println(ACLMessage.getContent());
				System.out.println("Usao je u petlju inform!");
				
				
				
				List<RealEstate> retList = new ArrayList<>();
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					retList = mapper.readValue(ACLMessage.getContent(), new TypeReference<List<RealEstate>>() {});
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(RealEstate r : retList) {
					System.out.println(r);
				}
				
				dataBean.getFilteredRealEstate().addAll(retList);
				
				
			}
		}
		
	}
	
	
	
	
	
	
	
}
