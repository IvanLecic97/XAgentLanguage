package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private List<UserMessage> messages;
	
	private String host;
	
	public User() {
		super();
	}


	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.messages = new ArrayList<UserMessage>();
	}
	
	public User(String username, String password, String host, List<UserMessage> messages) {
		super();
		this.username = username;
		this.password = password;
		this.messages = new ArrayList<UserMessage>();
		this.host = host;
		this.messages = messages;
	}
	
	public User(String username, String password, String host) {
		super();
		this.username = username;
		this.password = password;
		this.messages = new ArrayList<UserMessage>();
		this.host = host;
	}
	


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<UserMessage> getMessages() {
		return messages;
	}


	public void setMessages(List<UserMessage> messages) {
		this.messages = messages;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}