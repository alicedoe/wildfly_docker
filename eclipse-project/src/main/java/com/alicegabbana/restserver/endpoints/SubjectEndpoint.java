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

import com.alicegabbana.restserver.dto.SubjectDto;
import com.alicegabbana.restserver.entity.Subject;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.subject.SubjectResponse;

@Path("/subject")
public class SubjectEndpoint {
	
	@EJB
	SubjectResponse subjectResponse;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(SubjectEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSubject(Subject subject, @HeaderParam("token") String userToken) {
		
		List<String> subjectsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create subject"
	            		));
		if (authService.userHasActionList(userToken, subjectsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addSubjectServiceResponse = subjectResponse.createResponse(subject);
		return addSubjectServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(SubjectDto subjectDto, @HeaderParam("token") String userToken) {

		List<String> subjectsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read subject"
	            		));		
		if (authService.userHasActionList(userToken, subjectsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getSubjectServiceResponse = subjectResponse.getResponse(subjectDto);
		return getSubjectServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSubject(@HeaderParam("token") String userToken) {

		List<String> subjectsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read subject"
	            		));
		if (authService.userHasActionList(userToken, subjectsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllSubjectServiceResponse = subjectResponse.getAllResponse();
		return getAllSubjectServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSubject(SubjectDto subjectDto, @HeaderParam("token") String userToken) {

		List<String> subjectsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete subject"
	            		));
		if (authService.userHasActionList(userToken, subjectsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteSubjectResponse = subjectResponse.deleteResponse(subjectDto);
		return deleteSubjectResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSubject(SubjectDto subjectDto, @HeaderParam("token") String userToken) {

		List<String> subjectsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update subject"
	            		));
		if (authService.userHasActionList(userToken, subjectsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateSubjectServiceResponse = subjectResponse.updateResponse(subjectDto);
		return updateSubjectServiceResponse;
	}

}
