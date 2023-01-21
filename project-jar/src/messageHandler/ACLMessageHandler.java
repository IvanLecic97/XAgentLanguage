package messageHandler;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import agents.AgentManager;
import agents.ResearchAgent;
import model.ACLMessage;
import model.AID;

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
	
	
	
	
	
}
