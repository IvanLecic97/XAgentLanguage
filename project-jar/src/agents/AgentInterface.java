package agents;

import javax.ejb.Local;

import model.ACLMessage;

@Local
public interface AgentInterface {
	
	public void handleMessage(ACLMessage poruka);

}
