package messageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import agents.AgentManager;
import agents.ResearchAgent;
import model.ACLMessage;
import ws.WSEndpoint;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue2")
	})

public class MDBConsumerACL implements MessageListener{
	
	@EJB
	private WSEndpoint ws;
	
	//@EJB
	//private AgentManager agents;
	
	@EJB
	private ACLMessageHandler handler;

	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		
		try {
			ObjectMessage objectMessage = (ObjectMessage) msg;
			ACLMessage aclMessage = (ACLMessage) objectMessage.getObject();
			
			switch(aclMessage.getPerformative()) {
			case inform :
				handler.handlePerformative(aclMessage);
				break;
			case request :
				handler.handleRequest(aclMessage);
				break;
			default:
				break;
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
