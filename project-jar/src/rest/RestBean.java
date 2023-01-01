package rest;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;
import model.UserMessage;


public interface RestBean {
	
	@POST
	@Path("/users/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(User user);
	
	@GET
	@Path("/users/registered")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getRegisteredUsers();

	@POST
	@Path("/users/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user);
	
	@GET
	@Path("/users/loggedIn")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getLoggedInUsers();
	
	
	@POST
	@Path("/messages/all")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessageAll(UserMessage message);
	
	@POST
	@Path("/messages/user")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessageUser(UserMessage message);
	
	@GET
	@Path("/messages/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response messages(@PathParam("username") String username);
	
	
}