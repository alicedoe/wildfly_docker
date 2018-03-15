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
			Response addRoleServiceResponse = roleService.createRole(role);
			builder.status(addRoleServiceResponse.getStatus());
			builder.entity(addRoleServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Role role, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response getRoleServiceResponse = roleService.getRole(role);
			builder.status(getRoleServiceResponse.getStatus());
			builder.entity(getRoleServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRole(@HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response getAllRoleServiceResponse = roleService.getAllRole();
			builder.status(getAllRoleServiceResponse.getStatus());
			builder.entity(getAllRoleServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRole(Role role, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response deleteRoleRemoval = roleService.deleteRole(role);
			builder.status(deleteRoleRemoval.getStatus());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
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
