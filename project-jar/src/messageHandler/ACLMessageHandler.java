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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.AgentManager;
import agents.ResearchAgent;
import model.ACLMessage;
import model.AID;
import model.Performative;
import realEstate.RealEstate;
import realEstate.RealEstateDTO;
import rest.ResearchAgentRestRemote;

@LocalBean
@Remote(ACLMessageHandlerInterface.class)
@Stateless
public class ACLMessageHandler implements ACLMessageHandlerInterface{

	
	@EJB
	private AgentManager agents;
	
	
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
		// TODO Auto-generated method stub
		ResearchAgent sender = agents.getResearchAgentByName(message.getSender().getName());
		ResteasyClient client = new ResteasyClientBuilder().build();
		if(sender != null) {
			sender.handleMessage(message);
			System.out.println("Sender je " + sender.getAid().getName());
		}
		for(AID a : message.getReceivers()) {
			System.out.println("Receiver je " + a.getName());
			System.out.println("Content je " + message.getContent());
			ResteasyWebTarget rtarget = client.target("http://" + a.getHost().getAddress() + "/project-war/");
			ResearchAgentRestRemote ragent = (ResearchAgentRestRemote) rtarget.proxy(ResearchAgentRestRemote.class);
			ragent.addACLToAgent(message, a.getName());  //dodaj poruku agentu koji prima
			
			//pretraga entiteta
			List<RealEstate> retList = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			try {
				RealEstateDTO dto = (RealEstateDTO) mapper.readValue(message.getContent(), RealEstateDTO.class);
				retList = ragent.filter(dto);  ///zovem funkciju za filter entiteta
				ACLMessage aclMessage = new ACLMessage();
				AID[] receivers = new AID[0];
				receivers[0] = sender.getAid();  //postavljam sendera kao primaoca poruke 
				aclMessage.setReceivers(receivers);
				
				aclMessage.setSender(a); //postavljam sendera trenutnog agenta
				
				aclMessage.setContent(mapper.writeValueAsString(retList));
				System.out.println(mapper.writeValueAsString(retList));
				
				if(retList.size() == 0) {
					aclMessage.setPerformative(Performative.failure);
				} else {
					aclMessage.setPerformative(Performative.inform);
				}
				
				ragent.sendACLMessage(aclMessage);
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
}
