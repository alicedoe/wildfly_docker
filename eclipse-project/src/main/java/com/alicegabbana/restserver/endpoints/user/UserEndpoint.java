package com.alicegabbana.restserver.endpoints.user;

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
import com.alicegabbana.restserver.model.User;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.RoleService;
import com.alicegabbana.restserver.service.UserService;

@Path("/user")
public class UserEndpoint {
	
	@EJB
	UserService userService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(UserEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response addUserServiceResponse = userService.createUser(user);
			builder.status(addUserServiceResponse.getStatus());
			builder.entity(addUserServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(User user, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded) || authService.myUserAccount(userToken, user)) {
			Response getUserServiceResponse = userService.getUser(user);
			builder.status(getUserServiceResponse.getStatus());
			builder.entity(getUserServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUser(@HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response getAllUserServiceResponse = userService.getAllUser();
			builder.status(getAllUserServiceResponse.getStatus());
			builder.entity(getAllUserServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRole(User user, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response deleteUserResponse = userService.deleteUser(user);
			builder.status(deleteUserResponse.getStatus());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editRole(User user, @HeaderParam("UserToken") String userToken) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		
		if (authService.userCanDoListOfActions(userToken, actionsNeeded)) {
			Response updateUserServiceResponse = userService.updateUser(user);
			builder.status(updateUserServiceResponse.getStatus());
			builder.entity(updateUserServiceResponse.getEntity());
			
		} else {
			builder.status(Response.Status.FORBIDDEN);
		}
		return builder.build();
	}

}
