package com.alicegabbana.cahierenligne.endpoints;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.services.role.RoleResponse;

@Path("/role")
public class RoleEndpoint {
	
	@EJB
	RoleResponse roleResponse;
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAll() {
		Response response = roleResponse.getAll();
		return response;
	}

}
