package com.alicegabbana.restserver.endpoints;

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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.school.SchoolResponse;
import com.alicegabbana.restserver.utils.Actions;

import net.minidev.json.JSONObject;

@Path("/school")
public class SchoolEndpoint {
	
	@EJB
	SchoolResponse schoolResponse;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(SchoolEndpoint.class);
	
//	@Actions({"create school"})
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addSchool(@HeaderParam("token") String userToken) {		
//		Response addSchoolServiceResponse = schoolResponse.create(schoolDto);
//		return addSchoolServiceResponse;
	}
	
	@GET
	@Path("/get/{schoolId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSchool(@PathParam("schoolId") Long schoolId, @HeaderParam("token") String userToken) {		
		Response getSchoolServiceResponse = schoolResponse.get(schoolId);
		return getSchoolServiceResponse;
	}
	
	@Actions({"read school"})
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSchool(JSONObject json, @HeaderParam("token") String userToken) {
		
		System.out.println("entityParam : "+json);
		Response getAllSchoolServiceResponse = schoolResponse.getAll();
		return getAllSchoolServiceResponse;
	}
	

	@Actions({"delete school"})
	@DELETE
	@Path("/delete/{schoolId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSchool(@PathParam("schoolId") Long schoolId,  @HeaderParam("token") String userToken) {
		Response deleteSchoolResponse = schoolResponse.delete(schoolId);
		return deleteSchoolResponse;
	}
	
	@Actions({"update school"})
	@PUT
	@Path("/update/{schoolId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void editSchool() {
		
//		System.out.println("json body : "+json.getAsString("body"));
//		Response updateSchoolServiceResponse = schoolResponse.update(schoolDto);
//		return updateSchoolServiceResponse;
	}

}
