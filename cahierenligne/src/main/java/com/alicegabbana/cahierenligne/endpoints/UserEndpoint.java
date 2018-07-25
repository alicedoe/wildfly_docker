package com.alicegabbana.cahierenligne.endpoints;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.services.user.UserResponse;
import com.alicegabbana.cahierenligne.services.utils.Actions;

import net.minidev.json.JSONObject;

@Path("/user")
public class UserEndpoint {
	
	@EJB
	UserResponse userResponse;
	
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
}
