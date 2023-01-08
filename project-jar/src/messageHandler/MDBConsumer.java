package messageHandler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.Agent;
import agents.AgentManager;
import dataManager.UserDataBean;
import model.Node;
import model.User;
import model.UserMessage;
import rest.RestBean;
import server.NodeManager;
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
	
	@EJB
	private NodeManager nodeManager;
	
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
				if(sender != null) {
				sender.onMessage(msg);
		        ws.sendToOne(message.getSender(), jsonMessage);
		        ws.sendMessages(sender.getUser().getMessages());
		        
				}
				
		        Agent receiver = this.agents.getAgentByUsername(message.getReceiver());
		        //add to receiver messages
		        if(receiver != null) {
		        receiver.onMessage(msg);
		        ws.sendToOne(message.getReceiver(), jsonMessage);
		        ws.sendMessages(receiver.getUser().getMessages());
		        } else {
		        	message.setSentOverNetwork(true); // Only logging purposes here
		        	User user = data.getRegisteredUsers().get(message.getReceiver());
		        	ResteasyClient client = new ResteasyClientBuilder().build();
					ResteasyWebTarget rtarget = client.target("http://" + user.getHost() + "/project-war/rest/chat");
					RestBean rest = rtarget.proxy(RestBean.class);
					rest.sendMessageUser(message);
		        }
				
				
			} else { //message to all users
				for(Agent agent: this.agents.getAgents().values()) {
	        		agent.onMessage(msg);
	        	}
				ws.broadcast(jsonMessage);
				if (message.isSentOverNetwork()) {
	        		return;
	        	}
				message.setSentOverNetwork(true);
				// Send to all hosts over the network except this one
	        	ResteasyClient client = new ResteasyClientBuilder().build();
	        	for(Node node: nodeManager.getNodes()) {
	        		if (node.getAddress().equals(nodeManager.getNode().getAddress())) {
	        			continue;
	        		}
	        		
	        		ResteasyWebTarget rtarget = client.target("http://" + node.getAddress() + "/project-war/rest/chat");
					RestBean rest = rtarget.proxy(RestBean.class);
					rest.sendMessageAll(message);
	        	}
	        	// Send to master, it is not saved in hosts
	        	if (!nodeManager.getMaster().equals(nodeManager.getNode().getAddress())) {
	        		ResteasyWebTarget rtarget = client.target("http://" + nodeManager.getMaster() + "/project-war/rest/chat");
	        		RestBean rest = rtarget.proxy(RestBean.class);
					rest.sendMessageAll(message);
	        	}
	        }
			
		}catch (JMSException | JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	

}
