package com.alicegabbana.restserver.endpoints.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/user")
public class UserEndpoint {
	
	Logger logger = Logger.getLogger(UserEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser() {

		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Post : /user/add");
		}
		
		return "Created !";
	}
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUser() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Get : /user/{userId}");
		}
		
		return "User !";
	}
	
	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteUser() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Delete : /user/{userId}");
		}
		
		return "User !";
	}
	
	@GET
	@Path("/school/{schoolId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getSchoolUser() {	
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Get : /user/{schoolId}");
		}
		
		return "User from school !";
	}
	
	@PUT
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editUser() {	
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Put : /user/edit");
		}
		
		return "Edited !";
	}

}
