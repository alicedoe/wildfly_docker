package com.alicegabbana.restserver.endpoints.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.RoleService;

@Path("/role")
public class RoleEndpoint {
	
	@EJB
	RoleService roleService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(RoleEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRole(Role role, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {

			Role newRole = roleService.createRole(role);

			if (newRole != null) {
				builder.status(Response.Status.OK);
				builder.entity(newRole);
			} else {
				builder.status(Response.Status.CONFLICT);
			}
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@GET
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRole() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Get : /role/{roleId}");
		}
		
		return "User !";
	}
	
	@DELETE
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteRole() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Delete : /user/{roleId}");
		}
		
		return "User !";
	}
	
	@PUT
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editRole() {	
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Put : /role/edit");
		}
		
		return "Edited !";
	}

}
