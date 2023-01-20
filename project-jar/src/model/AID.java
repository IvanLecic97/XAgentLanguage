package model;

import java.io.Serializable;

public class AID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Node host;
	
	private AgentType type;
	
	public AID() {
		this.type = AgentType.research;
	}

	public AID(String name, Node host) {
		super();
		this.name = name;
		this.host = host;
		this.type = AgentType.research;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getHost() {
		return host;
	}

	public void setHost(Node host) {
		this.host = host;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

}
