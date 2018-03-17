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

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create role"
	            		));
		Response addUserServiceResponse = userService.createUserService( userToken, actionsNeeded, user );
		return addUserServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(User user, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		Response getUserServiceResponse = userService.getUserService( userToken, actionsNeeded, user );
		return getUserServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUser(@HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		Response getAllUserServiceResponse = userService.getAllUserService( userToken, actionsNeeded );
		return getAllUserServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRole(User user, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete role"
	            		));
		Response deleteUserResponse = userService.deleteUserService( userToken, actionsNeeded, user );
		return deleteUserResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editRole(User user, @HeaderParam("UserToken") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		Response updateUserServiceResponse = userService.updateUser( userToken, actionsNeeded, user );
		return updateUserServiceResponse;
	}

}
