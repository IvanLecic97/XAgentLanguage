package agents;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;



import model.User;
import model.UserMessage;

public class Agent {
	
	private String username;
	private User user;
	private AgentType name;
	
	public Agent() {
		
	}

	public Agent(String username, User user) {
		this.username = username;
		this.user = user;
		this.name.setName("chat");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	public AgentType getName() {
		return name;
	}

	public void setName(AgentType name) {
		this.name = name;
	}

	public void onMessage(Message msg) {
		try {
			 ObjectMessage objectMessage = (ObjectMessage) msg;
		        UserMessage message = (UserMessage) objectMessage.getObject();
		        message.setTimestamp(System.currentTimeMillis());
		        
		        if (message.getSender().equals(this.username)) {
					this.user.getMessages().add(message);
				} else if(message.getReceiver().equals(this.username) || message.getReceiver().equals("ALL")) {
					this.user.getMessages().add(message);
				}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		
	}

}
