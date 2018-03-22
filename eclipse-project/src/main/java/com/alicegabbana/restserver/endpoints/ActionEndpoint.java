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

import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.service.ActionService;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.RoleService;

@Path("/action")
public class ActionEndpoint {
	
	@EJB
	ActionService actionService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(ActionEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAction(Action action, @HeaderParam("UserToken") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create action"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addActionServiceResponse = actionService.createAction(action);
		return addActionServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Long id, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read action"
	            		));		
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getActionServiceResponse = actionService.getAction(id);
		return getActionServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAction(@HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read action"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllActionServiceResponse = actionService.getAllAction();
		return getAllActionServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAction(Action action, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete action"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteActionResponse = actionService.deleteAction(action);
		return deleteActionResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editAction(Action action, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update action"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateActionServiceResponse = actionService.updateAction(action);
		return updateActionServiceResponse;
	}

}
