package realEstateManager;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.RealEstate;

public interface RealEstateRestRemote {

	@GET
	@Path("/getRealEstate/{type}/{minPrice}/{maxPrice}/{minSize}/{maxSize}/{location}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RealEstate> getRealEstates(@PathParam("type") String type, @PathParam("minPrice") double minPrice,
			@PathParam("maxPrice") double maxPrice, @PathParam("minSize") double minSize, @PathParam("maxSize") double maxSize, @PathParam("location") String location);
	
}
