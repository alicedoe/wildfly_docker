package com.alicegabbana.restserver.endpoints;

import java.util.ArrayList;
import java.util.Arrays;
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

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.user.UserResponse;

import net.minidev.json.JSONObject;

@Path("/user")
public class UserEndpoint {
	
	@EJB
	UserResponse userResponse;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(UserEndpoint.class);
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(JSONObject body) {
		
		Response loginService = userResponse.login( body );
		return loginService;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(UserDto userDto, @HeaderParam("token") String userToken) {

//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"create user"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
		Response addUserServiceResponse = userResponse.create( userDto );
		return addUserServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(UserDto userDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response getUserServiceResponse = userResponse.get( userDto );
		return getUserServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUser(@HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response getAllUserServiceResponse = userResponse.getAll( );
		return getAllUserServiceResponse;
	}
	
	@GET
	@Path("/withrole")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserWithRole(RoleDto roleDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response getUserWithRoleServiceResponse = userResponse.getWithRole(roleDto);
		return getUserWithRoleServiceResponse;
	}
	
	@GET
	@Path("/fromkidsclass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFromKidsClass(KidsClassDto kidsClassDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response getUserFromKidsClassServiceResponse = userResponse.getFromKidsClass(kidsClassDto);
		return getUserFromKidsClassServiceResponse;
	}
	
	@GET
	@Path("/getactionsfromuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActionListFromUser(UserDto userDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response getUserFromKidsClassServiceResponse = userResponse.getActionListResponse(userDto);
		return getUserFromKidsClassServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(UserDto userDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response deleteUserResponse = userResponse.delete( userDto );
		return deleteUserResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(UserDto userDto, @HeaderParam("token") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response updateUserServiceResponse = userResponse.update( userDto );
		return updateUserServiceResponse;
	}
	
	@PUT
	@Path("/editmyaccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editMyAccount(UserDto user, @HeaderParam("token") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update user"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		Response updateUserServiceResponse = userResponse.updateMyAccount( user );
		return updateUserServiceResponse;
	}

}
