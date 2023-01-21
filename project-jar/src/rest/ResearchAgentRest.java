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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.Agent;
import agents.AgentManager;
import agents.ResearchAgent;
import dataManager.RealEstateDataBean;
import messageHandler.ACLMessageHandlerInterface;
import model.ACLMessage;
import model.AID;
import model.Performative;
import realEstate.RealEstate;
import realEstate.RealEstateDTO;
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
	
	public static List<RealEstate> realEstateList = new ArrayList<>();
	
	@EJB
	private AgentManager agents;
	
	@EJB
	private NodeManager nodeManager;
	
	@EJB
	private RealEstateDataBean dataBean;
	
	
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
	public Response sendACLMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		try {
		QueueConnection connection = (QueueConnection) connectionFactory.createConnection("guest", "guest.guest.1");
		QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueSender msgSender = session.createSender(queue);
		
		
		ObjectMessage objectMessage = session.createObjectMessage();
		objectMessage.setObject(message);
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
	
	



	@Override
	public Response filterRealEstate(RealEstateDTO realEstateDTO) {
		List<RealEstate> lista = dataBean.getRealEstateList();
		/*if(!realEstateDTO.getType().equals("empty") || !realEstateDTO.getType().equals(null)) {
			lista = lista.stream().filter(e -> e.getType().toString().equals(realEstateDTO.getType())).collect(Collectors.toList());
		} 
		if(realEstateDTO.getMinPrice() != 0) {
			lista = lista.stream().filter(e -> e.getPrice() >= realEstateDTO.getMinPrice()).collect(Collectors.toList());
		}
		if(realEstateDTO.getMaxPrice() != 0) {
			lista = lista.stream().filter(e -> e.getPrice() <= realEstateDTO.getMaxPrice()).collect(Collectors.toList());
		}
		if(realEstateDTO.getMinSize() != 0) {
			lista = lista.stream().filter(e -> e.getSize() >= realEstateDTO.getMinSize()).collect(Collectors.toList());
		}
		if(realEstateDTO.getMaxSize() != 0) {
			lista = lista.stream().filter(e -> e.getSize() <= realEstateDTO.getMaxSize()).collect(Collectors.toList());
		}
		if(!realEstateDTO.getLocation().equals("empty") || !realEstateDTO.getLocation().equals(null)) {
			lista = lista.stream().filter(e -> e.getLocation().equals(realEstateDTO.getLocation())).collect(Collectors.toList());
		} */
		
		lista = lista.stream().filter(e -> e.getType().toString().equals(realEstateDTO.getType()))
				.filter(e -> e.getPrice() >= realEstateDTO.getMinPrice())
				.filter(e -> e.getPrice() <= realEstateDTO.getMaxPrice())
				.filter(e -> e.getSize() >= realEstateDTO.getMinSize())
				.filter(e -> e.getSize() <= realEstateDTO.getMaxSize())
				.filter(e -> e.getLocation().equals(realEstateDTO.getLocation())).collect(Collectors.toList());
				
		ACLMessage message = new ACLMessage();
		ObjectMapper mapper = new ObjectMapper();
		String jsonMessage;
		try {
			jsonMessage = mapper.writeValueAsString(realEstateDTO);
			message.setContent(jsonMessage);
			message.setReceivers(agents.getReceiversForMessage(realEstateDTO.getUsername()));
			message.setSender(agents.getResearchAgentByName(realEstateDTO.getUsername()).getAid());
			message.setPerformative(Performative.request);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Response.status(Response.Status.OK).entity(lista).build(); //sendACLMessage(message);
	}



	@Override
	public Response setAgentRunning(String type, String name) {
		// TODO Auto-generated method stub
		ResearchAgent agent = agents.getResearchAgentByName(name);
		agents.setResearchAgentRunning(agent);
		if(agent.getAid() == null) {
			System.out.println("Null je");
		}
		
		return Response.status(Response.Status.OK).build();
	}



	@Override
	public Response stopAgent(String aid) {
		// TODO Auto-generated method stub
		ResearchAgent agent = agents.getResearchAgentByName(aid);
		agents.stopAgentRunning(agent.getAid());
		return Response.status(Response.Status.OK).build();
	}
	
	
	@GET
	@Path("/testJNDI")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testJNDI() {
		String retVal = "Bezveze";
		try {
		String jndi = "java:app/project-jar/ACLMessageHandler!messageHandler.ACLMessageHandlerInterface";
		InitialContext context = new InitialContext();
		ACLMessageHandlerInterface msgInterface = (ACLMessageHandlerInterface) context.lookup("java:app/project-jar/ACLMessageHandler!messageHandler.ACLMessageHandlerInterface");
		retVal = msgInterface.testJNDI();
		
		} catch(NamingException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(retVal).build();
	}
	
	
	@GET
	@Path("/testGetAgents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testGetAgents() {
		
		Agent user = agents.getAgentByUsername("ika97");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget rtarget = client.target("http://" + user.getUser().getHost() + "/project-war/rest/agentRest");
		ResearchAgentRestRemote researchRest = rtarget.proxy(ResearchAgentRestRemote.class);
		
		return Response.status(Response.Status.OK).entity(researchRest.getRunningAgents()).build();
		
	}
	
	
	
	
	
	

}
