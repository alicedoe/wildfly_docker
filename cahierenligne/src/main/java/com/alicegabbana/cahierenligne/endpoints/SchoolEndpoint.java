package com.alicegabbana.cahierenligne.endpoints;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.SchoolDto;
import com.alicegabbana.cahierenligne.services.school.SchoolResponse;
import com.alicegabbana.cahierenligne.services.utils.Actions;

@Path("/school")
public class SchoolEndpoint {
	
	@EJB
	SchoolResponse schoolResponse;
	
	@POST
	@Actions({"create school"})
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(SchoolDto schoolDto) {
		Response response = schoolResponse.create( schoolDto );
		return response;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getall() {
		Response response = schoolResponse.getAll();
		return response;
	}
	
	@PUT
	@Actions({"update all school"})
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(SchoolDto schoolDto) {
		Response response = schoolResponse.update(schoolDto);
		return response;
	}
	
	@DELETE
	@Actions({"delete all school"})
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Long id) {
		Response response = schoolResponse.delete(id);
		return response;
	}
	
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Response response = schoolResponse.get(id);
		return response;
	}
}
