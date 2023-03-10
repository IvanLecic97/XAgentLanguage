package agents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.ACLMessage;
import model.AID;
import ws.WSEndpoint;

@Stateful
public class ResearchAgent extends AgentClass implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<ACLMessage> aclMessages;
	
	
	public static String JNDISTRING = "java:app/project-jar/ResearchAgent!agents.ResearchAgent";
	
	
	public ResearchAgent() {
		super();
		this.aclMessages = new ArrayList<ACLMessage>();
	}
	
	public ResearchAgent(List<ACLMessage> aclMessages) {
		super();
		this.aclMessages = aclMessages;
	}

	

	public List<ACLMessage> getAclMessages() {
		return aclMessages;
	}

	public void setAclMessages(List<ACLMessage> aclMessages) {
		this.aclMessages = aclMessages;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		/*System.out.println("Na pocetku je petlje");
		//System.out.println(this.getAid().equals(null) ? "Null je majku mu prcim" : "nije null");
		System.out.println(this.getAid().getName()+ " a ovo je ime");
		if(message.getSender().getName().equals(this.getAid().getName())) {  ///dodaj posaljiocu agentu poruku
			System.out.println("Uso je u petlju ovu");
			this.getAclMessages().add(message);
			System.out.println(this.getNames(message.getReceivers()));
		} else if(this.getNames(message.getReceivers()).contains(this.getAid().getName())){
			System.out.println("Usao je u else petljicu");
			for(AID a : message.getReceivers()) {  ///dodaj primaocu ako se nalazi u primaocima poruke
				if(a.getName().equals(this.getAid().getName())) {
					this.getAclMessages().add(message);
				}
			} 
		
	} */
		this.getAclMessages().add(message);
		
		/*ObjectMapper mapper = new ObjectMapper();
		String packageString = "";
		try {
			packageString = mapper.writeValueAsString(this.getAclMessages());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String wsString = "java:global/project-ear/project-war/WSEndpoint!ws.WSEndpoint";
		try {
			Context context = new InitialContext();
			WSEndpoint ws = (WSEndpoint) context.lookup(wsString);
			ws.sendToOne(this.getAid().getName(), packageString);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		
}
	
	public List<String> getNames(AID[] list){
		List<String> retVal = new ArrayList<String>();
		for(AID a : list) {
			retVal.add(a.getName());
		}
		return retVal;
	}

}