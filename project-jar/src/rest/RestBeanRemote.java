package rest;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.AgentManager;
import agents.ResearchAgent;
import dataManager.UserDataBean;
import model.AID;
import model.Node;
import model.User;
import model.UserMessage;
import server.ServersRestLocal;
//import ws.WSEndpoint;
import ws.WSEndpoint;



@Stateless
@LocalBean
@Path("/chat")
@Remote(RestBean.class)
public class RestBeanRemote implements RestBean {
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/exported/jms/queue/mojQueue")
	private Queue queue;
	
	@EJB
	private UserDataBean usersData;
	
	@EJB
	private WSEndpoint ws;
	
	@EJB
	private AgentManager agents;
	
	@EJB
	private ServersRestLocal serversRest;

	@Override
	public String register(User user) {
		if(usersData.getRegisteredUsers().containsKey(user.getUsername())) {
			return "That username already exists, pick a new one!";
		}
		else {
		Node node = serversRest.getNode();
		User newUser = new User(user.getUsername(), user.getPassword(), node.getAddress());
		usersData.getRegisteredUsers().put(newUser.getUsername(), newUser);
		agents.createNewAgent(newUser.getUsername(), newUser);
		
		AID aid = new AID();
		aid.setHost(node);
		aid.setName(newUser.getUsername());
		agents.createNewResearchAgent(aid);
		
		serversRest.informNodesNewUserRegistered(newUser);
		
		return "Registered";
		}
	}

	@Override
	public List<String> getRegisteredUsers() {
		// TODO Auto-generated method stub
		List<String> retVal = new ArrayList<>();
		for(String s : usersData.getRegisteredUsers().keySet()) {
			retVal.add(s);
		}
		return retVal;
	}

	@Override
	public Response login(User user) {
		User regUser = usersData.getRegisteredUsers().get(user.getUsername());
		if(regUser == null || !regUser.getPassword().equals(user.getPassword())) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Bad username or password, please try again!").build();
		}
		usersData.getLoggedInUsers().put(regUser.getUsername(), regUser);
		///samo testiram pokrenutost agenata
		ResearchAgent agent = agents.getResearchAgentByName(regUser.getUsername());
		agents.setResearchAgentRunning(agent);
		
		
		
		serversRest.informNodesAboutLoggedInUsers();
		
		//WebSocket
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonMessage = mapper.writeValueAsString(usersData.getLoggedInUsers().values());
			ws.updateLoggedInUsers(jsonMessage);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(regUser).build();
	}

	@Override
	public List<String> getLoggedInUsers() {
		// TODO Auto-generated method stub
		List<String> retVal = new ArrayList<>();
		for(String s : usersData.getLoggedInUsers().keySet()) {
			retVal.add(s);
		}
		return retVal;
	}

	@Override
	public Response sendMessageAll(UserMessage message) {
		if (message == null || message.getSender() == null || !message.getReceiver().equals("ALL")) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid message").build();
		}
		if (!usersData.getLoggedInUsers().containsKey(message.getSender())){
			return Response.status(Response.Status.BAD_REQUEST).entity("Not logged in").build();
		}
		
		try {
			QueueConnection connection = (QueueConnection) connectionFactory.createConnection("guest", "guest.guest.1");
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender msgSender = session.createSender(queue);
			
			ObjectMessage msg = session.createObjectMessage();
			msg.setObject(message);
			msgSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response sendMessageUser(UserMessage message) {
		if (message == null || message.getSender() == null || message.getReceiver() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid message").build();
		}
		User sender = usersData.getLoggedInUsers().get(message.getSender());
		if (sender == null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Not logged in").build();
		}

		User reciever = usersData.getRegisteredUsers().get(message.getReceiver());
		if (reciever == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid reciever").build();
		}
		
		try {
			QueueConnection connection = (QueueConnection) connectionFactory.createConnection("guest", "guest.guest.1");
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender msgSender = session.createSender(queue);
			
			ObjectMessage msg = session.createObjectMessage();
			msg.setObject(message);
			msgSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.OK).build();
	}
	
	
	@Override
	public Response messages(@PathParam("username") String username) {
		User user = usersData.getRegisteredUsers().get(username);
		if (user == null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user").build();
		}
		List<UserMessage> messages = user.getMessages();
		
		return Response.status(Response.Status.OK).entity(messages).build();
	}
	
	
	
	@DELETE
	@Path("/users/loggedIn/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@PathParam("username") String username) {
		User checkLoggedIn = usersData.getLoggedInUsers().get(username);
		if (checkLoggedIn == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Not logged in").build();
		}
		
		usersData.getLoggedInUsers().remove(username);
		
		serversRest.informNodesAboutLoggedInUsers();
		
		// WebSocket
		ObjectMapper mapper = new ObjectMapper();
        try {
			String jsonMessage = mapper.writeValueAsString(usersData.getLoggedInUsers().values());
			ws.updateLoggedInUsers(jsonMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// Log ------------
		System.out.println("User { " + username + " } logged OUT");
		// -----------------

		return Response.status(Response.Status.OK).build();
	}
	
	
	
	

}