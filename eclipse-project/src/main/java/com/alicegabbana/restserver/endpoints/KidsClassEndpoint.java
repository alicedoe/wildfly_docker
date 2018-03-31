package com.alicegabbana.restserver.endpoints;

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

import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.LevelDto;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.KidsClassService;

@Path("/kidsclass")
public class KidsClassEndpoint {
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(KidsClassEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addKidsClass(KidsClassDto kidsClassDto, @HeaderParam("UserToken") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addKidsClassServiceResponse = kidsClassService.createResponse(kidsClassDto);
		return addKidsClassServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getKidsClass(KidsClassDto kidsClassDto, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read kidsClass"
	            		));		
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getKidsClassServiceResponse = kidsClassService.getResponse(kidsClassDto);
		return getKidsClassServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllKidsClass(@HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllKidsClassServiceResponse = kidsClassService.getAllResponse();
		return getAllKidsClassServiceResponse;
	}
	
	@GET
	@Path("/fromschool")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKidsClassFromSchool(SchoolDto schoolDto, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getKidsClassServiceResponse = kidsClassService.getFromSchoolResponse(schoolDto);
		return getKidsClassServiceResponse;
	}
	
	@GET
	@Path("/withlevel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKidsClassFromLevel(LevelDto levelDto, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getKidsClassServiceResponse = kidsClassService.getWithLevelResponse(levelDto);
		return getKidsClassServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteKidsClass(Long kidsClassId, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteKidsClassResponse = kidsClassService.deleteResponse(kidsClassId);
		return deleteKidsClassResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editKidsClass(KidsClassDto kidsClassDto, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update kidsClass"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateKidsClassServiceResponse = kidsClassService.updateResponse(kidsClassDto);
		return updateKidsClassServiceResponse;
	}

}
