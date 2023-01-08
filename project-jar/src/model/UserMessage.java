package model;

import java.io.Serializable;

public class UserMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String receiver;
	
	private String sender;
	
	private long timestamp;
	
	private String content;
	
	private String subject;
	
	private boolean sentOverNetwork = false; // Prevents loop when sending to all users over network
	
	public UserMessage() {
		super();
	}

	public UserMessage(String receiver, String sender, long timestamp, String content, String subject) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.timestamp = timestamp;
		this.content = content;
		this.subject = subject;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String reciever) {
		this.receiver = reciever;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public boolean isSentOverNetwork() {
		return sentOverNetwork;
	}

	public void setSentOverNetwork(boolean sentOverNetwork) {
		this.sentOverNetwork = sentOverNetwork;
	}
	
	
}
