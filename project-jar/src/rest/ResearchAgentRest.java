package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.AgentManager;
import agents.ResearchAgent;
import model.ACLMessage;
import model.AID;
import model.Performative;
import server.NodeManager;

@Stateless
@LocalBean
@Path("/agentRest")
@Remote(ResearchAgentRestRemote.class)
public class ResearchAgentRest implements ResearchAgentRestRemote {

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/exported/jms/queue/mojQueue2")
	private Queue queue;
	
	
	
	@EJB
	private AgentManager agents;
	
	@EJB
	private NodeManager nodeManager;
	
	
	
	@Override
	public Response getAgentClasses() {
		// TODO Auto-generated method stub
		List<String> retList = new ArrayList<String>();
		agents.getResearchAgents().keySet().stream().forEach(e -> retList.add(e.getType().toString()));
		return Response.status(Response.Status.OK).entity(retList).build();
	}



	@Override
	public Response getRunningAgents() {
		// TODO Auto-generated method stub
		List<ResearchAgent> retVal = agents.getRunningResearchAgents().values().stream().collect(Collectors.toList());
		return Response.status(Response.Status.OK).entity(retVal).build();
	}
	
	@GET
	@Path("/testGetAID")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByAID() {
		ResearchAgent agent = agents.getResearchAgentByName("ika97");
		//aid.setName("ika97");
		//aid.setHost(nodeManager.getNode());
		//ResearchAgent agent = agents.getResearchAgentByAID(aid);
		return Response.status(Response.Status.OK).entity(agents.getReceiversForMessage("ika97")).build();
	}



	@Override
	public Response sendACLMessage(String username) {
		// TODO Auto-generated method stub
		try {
		QueueConnection connection = (QueueConnection) connectionFactory.createConnection("guest", "guest.guest.1");
		QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueSender msgSender = session.createSender(queue);
		
		ACLMessage aclMessage = new ACLMessage();
		AID[] receivers = agents.getReceiversForMessage(username);
		ResearchAgent sender = agents.getResearchAgentByName(username);
		System.out.println(sender.getAid().getName() + " ime");
		System.out.println(sender.getAid().getHost().getAddress() + " adresa");
		System.out.println(sender.getAid().getType() + " tip");
		aclMessage.setSender(sender.getAid());
		aclMessage.setReceivers(receivers);
		aclMessage.setPerformative(Performative.request);
		aclMessage.setContent("Bezveze brapice");
		
		ObjectMessage objectMessage = session.createObjectMessage();
		objectMessage.setObject(aclMessage);
		msgSender.send(objectMessage);
		
		
		} catch(JMSException e) {
			e.printStackTrace();
		} 
		
		return Response.status(Response.Status.OK).build();
	}
	
	
	@GET
	@Path("/testGetReceivers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testGetReceivers() {
		
		AID[] aids = agents.getReceiversForMessage("ika97");
		
		return Response.status(Response.Status.OK).entity(aids).build();
	}
	
	
	
	
	

}
