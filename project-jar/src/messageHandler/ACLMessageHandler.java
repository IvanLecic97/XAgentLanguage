package messageHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ws.WSEndpoint;
import ws.WSEndpoint2;

@LocalBean
@Remote(ACLMessageHandlerInterface.class)
@Stateless
public class ACLMessageHandler implements ACLMessageHandlerInterface{

	
	@EJB
	private AgentManager agents;
	
	@EJB
	private RealEstateDataBean dataBean;
	
	@EJB
	private WSEndpoint ws;
	
	@EJB
	private WSEndpoint2 ws2;
	
	
	@Override
	public void handlePerformative(ACLMessage message) {
		// TODO Auto-generated method stub
		ResearchAgent sender = agents.getResearchAgentByName(message.getSender().getName());
		dataBean.setFilteredRealEstate(new HashSet<>());
		
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
		dataBean.setFilteredRealEstate(new HashSet<>());
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
		
		//for(RealEstate r : retList) {
		//	dataBean.addElement(r);
		//}
		
		
		
		String stringMessage = "";
		
		try {
			stringMessage = mapper.writeValueAsString(retList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String packageString = "";
		try {
			packageString = mapper.writeValueAsString(sender.getAclMessages());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ws.sendEntities(packageString);
		
		ws2.sendEntities(stringMessage);
		
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
				ResteasyWebTarget target1 = client1.target("http://" + message.getSender().getHost().getAlias() + "/project-war/rest/agentRest");
				ResearchAgentRestRemote ragent1 = target1.proxy(ResearchAgentRestRemote.class);
				ragent1.sendACLMessage(returnMessage);
				
				
				
				String packageString = "";
				try {
					packageString = mapper.writeValueAsString(receiver.getAclMessages());
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ws.sendToOne(receiver.getAid().getName(), packageString);
				
				
				
				ResteasyWebTarget target = client.target("http://" + receiver.getAid().getHost().getAlias() + "/project-war/rest/agentRest");
				ResearchAgentRestRemote ragent = target.proxy(ResearchAgentRestRemote.class);
				ragent.sendACLMessage(returnMessage);
				
				
				///linije koda iznad opisuju vracanje poruke posiljaocu koja sadrzi filtrirane liste, redoslijed pozivanja je bitan
				///jer ako prvo posaljemo korisniku masine na kojoj se filer radi, nece uci u naredni poziv ResteasyClient-a 
				///nego ce MDB da reaguje i krene ispocetka
				
				
				
				
			}else {
			ResteasyWebTarget target = client.target("http://" + a.getHost().getAlias() + "/project-war/rest/agentRest");
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
			
			ObjectMapper mapper = new ObjectMapper();
			
			String packageString = "";
			try {
				packageString = mapper.writeValueAsString(sender.getAclMessages());
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ws.sendToOne(sender.getAid().getName(), packageString);
			
		}
		
		for(AID a : ACLMessage.getReceivers()) {
			ResearchAgent receiver = this.agents.getResearchAgentByName(a.getName());
			//System.out.println("ime primaca je " + receiver.getAid().getName());
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
				Set<RealEstate> retSet = new HashSet<>(retList);
				for(RealEstate r : retList) {
					dataBean.addElement(r);
				}
				
				
				//String packageString = "";
				try {
					String	packageString = mapper.writeValueAsString(receiver.getAclMessages());
					System.out.println("User kome saljem websocket je " + receiver.getAid().getName()) ;
					System.out.println("Duzina njegovih acl poruka je " + receiver.getAclMessages().size());
					//for(ACLMessage msg : receiver.getAclMessages()) {
					//	System.out.println(msg.getPerformative() + " Performative");
					//}
					System.out.println(packageString);
					ws.sendEntities(packageString);
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			
				String stringMessage = "";
				try {
					stringMessage = mapper.writeValueAsString(retList);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ws2.sendEntities(stringMessage);
				
				
				
			}
		}
		
	}
	
	
	
	
	
	
	
}
