package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.ACLMessage;
import model.UserMessage;
import realEstate.RealEstateDTO;

public interface ResearchAgentRestRemote {

	public static String JNDISTRING = "java:global/project-ear/project-jar/ResearchAgentRest!rest.ResearchAgentRestRemote";
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentClasses();
	
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRunningAgents();
	
	@POST
	@Path("/messages")
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response sendACLMessage(ACLMessage message);
	
	@PUT
	@Path("/agents/running/{type}/{name}")
	public Response setAgentRunning(@PathParam("type") String type, @PathParam("name") String name);
	
	@DELETE
	@Path("/agents/running/{aid}")
	public Response stopAgent(@PathParam("aid") String aid);
	
	@POST
	@Path("/realEstate/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterRealEstate(RealEstateDTO realEstateDTO);
	
	
	
	
	
}