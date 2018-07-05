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

import com.alicegabbana.restserver.dto.TagDto;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.tag.TagResponse;

@Path("/tag")
public class TagEndpoint {
	
	@EJB
	TagResponse tagResponse;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(TagEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTag(TagDto tagDto, @HeaderParam("token") String userToken) {
		
		List<String> tagsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create tag"
	            		));
		if (authService.userHasActionList(userToken, tagsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addTagServiceResponse = tagResponse.create(tagDto);
		return addTagServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(TagDto tagDto, @HeaderParam("token") String userToken) {

		List<String> tagsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read tag"
	            		));		
		if (authService.userHasActionList(userToken, tagsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getTagServiceResponse = tagResponse.getTag(tagDto);
		return getTagServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTag(@HeaderParam("token") String userToken) {

		List<String> tagsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read tag"
	            		));
		if (authService.userHasActionList(userToken, tagsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllTagServiceResponse = tagResponse.getAllTag();
		return getAllTagServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteTag(TagDto tagDto, @HeaderParam("token") String userToken) {

		List<String> tagsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete tag"
	            		));
		if (authService.userHasActionList(userToken, tagsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteTagResponse = tagResponse.deleteTag(tagDto);
		return deleteTagResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editTag(TagDto tagDto, @HeaderParam("token") String userToken) {

		List<String> tagsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update tag"
	            		));
		if (authService.userHasActionList(userToken, tagsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateTagServiceResponse = tagResponse.update(tagDto);
		return updateTagServiceResponse;
	}

}
