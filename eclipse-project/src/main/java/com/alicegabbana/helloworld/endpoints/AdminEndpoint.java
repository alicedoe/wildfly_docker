package com.alicegabbana.helloworld.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/hello")
public class AdminEndpoint {

	Logger logger = Logger.getLogger(AdminEndpoint.class);
	
	@GET
	@Path("/toto")
	@Produces(MediaType.APPLICATION_JSON)
	public String toto() {
		return "Hi !";
	}
	
	
}
