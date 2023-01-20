package messageHandler;

import javax.ejb.Local;

import model.ACLMessage;


public interface ACLMessageHandlerInterface {

	public void handlePerformative(ACLMessage message);
	
}
