package com.alicegabbana.restserver.endpoints;

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
import com.alicegabbana.restserver.dto.NewUserDto;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.user.UserResponse;
import com.alicegabbana.restserver.services.user.UserService;
import com.alicegabbana.restserver.utils.Actions;

import net.minidev.json.JSONObject;

@Path("/user")
public class UserEndpoint {
	
	@EJB
	UserResponse userResponse;
	
	@EJB
	AuthService authService;
	
	@EJB
	UserService userService;
	
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
	@Actions({"create user"})
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(NewUserDto newUserDto) {
		Response addUserServiceResponse = userResponse.create( newUserDto );
		return addUserServiceResponse;
	}
	
	@GET
	@Actions({"read user"})
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(UserDto userDto) {
		Response getUserServiceResponse = userResponse.get( userDto );
		return getUserServiceResponse;
	}
	
	@GET
	@Actions({"read user"})
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUser(@HeaderParam("token") String userToken) {
		Response getAllUserServiceResponse = userResponse.getAll( );
		return getAllUserServiceResponse;
	}
	
	@GET
	@Actions({"read user"})
	@Path("/withrole")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserWithRole(RoleDto roleDto, @HeaderParam("token") String userToken) {
		Response getUserWithRoleServiceResponse = userResponse.getWithRole(roleDto);
		return getUserWithRoleServiceResponse;
	}
	
	@GET
	@Actions({"read user"})
	@Path("/fromkidsclass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFromKidsClass(KidsClassDto kidsClassDto, @HeaderParam("token") String userToken) {
		Response getUserFromKidsClassServiceResponse = userResponse.getFromKidsClass(kidsClassDto);
		return getUserFromKidsClassServiceResponse;
	}
	
	@GET
	@Actions({"read user"})
	@Path("/getactionsfromuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActionListFromUser(UserDto userDto, @HeaderParam("token") String userToken) {
		Response getUserFromKidsClassServiceResponse = userResponse.getActionListResponse(userDto);
		return getUserFromKidsClassServiceResponse;
	}
	
	@DELETE
	@Actions({"delete user"})
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(UserDto userDto, @HeaderParam("token") String userToken) {
		Response deleteUserResponse = userResponse.delete( userDto );
		return deleteUserResponse;
	}
	
	@PUT
	@Actions({"update user"})
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(UserDto userDto, @HeaderParam("token") String userToken) {
		Response updateUserServiceResponse = userResponse.update( userDto );
		return updateUserServiceResponse;
	}
	
	@PUT
	@Actions({"update user"})
	@Path("/editmyaccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editMyAccount(UserDto user, @HeaderParam("token") String userToken) {
		Response updateUserServiceResponse = userResponse.updateMyAccount( user );
		return updateUserServiceResponse;
	}
}
