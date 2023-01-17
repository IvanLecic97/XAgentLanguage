package agents;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

import model.ACLMessage;
import model.AID;

@Stateful
public class ResearchAgent extends AgentClass{

	private AID id;
	
	private List<ACLMessage> aclMessages;
	
	public  ResearchAgent() {
		this.aclMessages = new ArrayList<ACLMessage>();
	}
	
	
	
	
	public AID getId() {
		return id;
	}




	public void setId(AID id) {
		this.id = id;
	}


	


	public List<ACLMessage> getAclMessages() {
		return aclMessages;
	}




	public void setAclMessages(List<ACLMessage> aclMessages) {
		this.aclMessages = aclMessages;
	}

	



	@Override
	public void handleMessage(ACLMessage poruka) {
		// TODO Auto-generated method stub
		
	}

	
	
}
