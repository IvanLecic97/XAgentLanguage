package model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;

public class ACLMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Performative performative;
	
	private AID sender;
	
	private AID[] receivers;
	
	private AID replyTo;
	
	private String content;
	
	private Object contentObj;
	
	private HashMap<String, Object> userArgs;
	
	private String language;
	
	private String encoding;
	
	private String ontology;
	
	private String protocol;
	
	private String conversationId;
	
	private String replyWith;
	
	private String inReplyTo;
	
	private long replyBy;
	
	
	public ACLMessage() {
		this.userArgs = new HashMap<String, Object>();
		this.receivers = new AID[0];
		this.replyBy = Instant.now().toEpochMilli();
	}


	public Performative getPerformative() {
		return performative;
	}


	public void setPerformative(Performative performative) {
		this.performative = performative;
	}


	public AID getSender() {
		return sender;
	}


	public void setSender(AID sender) {
		this.sender = sender;
	}


	public AID[] getReceivers() {
		return receivers;
	}


	public void setReceivers(AID[] receivers) {
		this.receivers = receivers;
	}


	public AID getReplyTo() {
		return replyTo;
	}


	public void setReplyTo(AID replyTo) {
		this.replyTo = replyTo;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Object getContentObj() {
		return contentObj;
	}


	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}


	public HashMap<String, Object> getUserArgs() {
		return userArgs;
	}


	public void setUserArgs(HashMap<String, Object> userArgs) {
		this.userArgs = userArgs;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public String getOntology() {
		return ontology;
	}


	public void setOntology(String ontology) {
		this.ontology = ontology;
	}


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public String getConversationId() {
		return conversationId;
	}


	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}


	public String getReplyWith() {
		return replyWith;
	}


	public void setReplyWith(String replyWith) {
		this.replyWith = replyWith;
	}


	public String getInReplyTo() {
		return inReplyTo;
	}


	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}


	public long getReplyBy() {
		return replyBy;
	}


	public void setReplyBy(long replyBy) {
		this.replyBy = replyBy;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
