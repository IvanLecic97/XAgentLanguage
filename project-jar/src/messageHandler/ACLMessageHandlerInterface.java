package messageHandler;

import javax.ejb.Local;

import model.ACLMessage;


public interface ACLMessageHandlerInterface {

	public void handlePerformative(ACLMessage message);
	
	public void handleRequest(ACLMessage message);
	
	public void handleInform(ACLMessage ACLMessage);
	
	public String testJNDI();
}
