package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.UserMessage;

public interface ResearchAgentRestRemote {

	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentClasses();
	
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRunningAgents();
	
	@POST
	@Path("/messages/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response sendACLMessage(@PathParam("username") String username);
	
}
