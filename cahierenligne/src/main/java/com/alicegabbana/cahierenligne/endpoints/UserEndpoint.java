package com.alicegabbana.cahierenligne.endpoints;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
		Response response = userResponse.login( body );
		return response;
	}
	
	@POST
	@Actions({"create user"})
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(NewUserDto newUserDto) {
		Response response = userResponse.create( newUserDto );
		return response;
	}
	
	@GET
	@Actions({"read all user"})
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAll() {
		Response response = userResponse.getAll();
		return response;
	}
	
	@GET
	@Actions({"read all user"})
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Response response = userResponse.get(id);
		return response;
	}
	
	@DELETE
	@Actions({"delete all user"})
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Long id) {
		Response response = userResponse.delete(id);
		return response;
	}
	
}
