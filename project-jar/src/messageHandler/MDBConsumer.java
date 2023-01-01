package messageHandler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.Agent;
import agents.AgentManager;
import dataManager.UserDataBean;
import model.UserMessage;
import ws.WSEndpoint;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;


@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue")
	})

public class MDBConsumer implements MessageListener {

	@EJB
	private WSEndpoint ws;
	
	@EJB
	private UserDataBean data;
	
	@EJB
	private AgentManager agents;
	
	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		try {
			ObjectMessage object = (ObjectMessage) msg;
			UserMessage message = (UserMessage) object.getObject();
		
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonMessage = mapper.writeValueAsString(message);
			
			if(!message.getReceiver().equals("ALL")) {
				Agent sender  = this.agents.getAgentByUsername(message.getSender());
				
				//send to single user and add to senders messages
				sender.onMessage(msg);
		        ws.sendToOne(message.getSender(), jsonMessage);
		        ws.sendMessages(sender.getUser().getMessages());
		        
		        Agent receiver = this.agents.getAgentByUsername(message.getReceiver());
		        
		        //add to receiver messages
		        receiver.onMessage(msg);
		        ws.sendToOne(message.getReceiver(), jsonMessage);
		        ws.sendMessages(receiver.getUser().getMessages());
				
				
			} else { //message to all users
				for(Agent agent: this.agents.getAgents().values()) {
	        		agent.onMessage(msg);
	        	}
				ws.broadcast(jsonMessage);
				
			}
		}catch (JMSException | JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	

}
