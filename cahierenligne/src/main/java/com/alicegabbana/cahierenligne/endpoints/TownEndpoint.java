package com.alicegabbana.cahierenligne.endpoints;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.entities.Town;
import com.alicegabbana.cahierenligne.services.town.TownResponse;
import com.alicegabbana.cahierenligne.services.utils.Actions;

@Path("/town")
public class TownEndpoint {
	
	@EJB
	TownResponse townResponse;
	
	@POST
	@Actions({"create town"})
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTown(Town town) {
		
		Response response = townResponse.create( town.getName() );
		return response;
	}
	
	@DELETE
	@Actions({"delete all town"})
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteTown(Town town) {
		
		Response response = townResponse.delete( town.getId() );
		return response;
	}
	
	@PUT
	@Actions({"update all town"})
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTown(Town town) {
		
		Response response = townResponse.update( town );
		return response;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllTown() {
		
		Response response = townResponse.getAll();
		return response;
	}

}
