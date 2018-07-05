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

import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.role.RoleResponse;

@Path("/role")
public class RoleEndpoint {
	
	@EJB
	RoleResponse roleResponse;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(RoleEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRole(RoleDto roleDto, @HeaderParam("token") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addRoleServiceResponse = roleResponse.createResponse(roleDto);
		return addRoleServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(RoleDto roleDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));		
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getRoleServiceResponse = roleResponse.getByIdResponse(roleDto);
		return getRoleServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRole(@HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllRoleServiceResponse = roleResponse.getAllRoleResponse();
		return getAllRoleServiceResponse;
	}
	
	@GET
	@Path("/getaction")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAction(RoleDto roleDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getActionFromRoleServiceResponse = roleResponse.getActionFromRoleResponse(roleDto);
		return getActionFromRoleServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRole(RoleDto roleDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteRoleResponse = roleResponse.deleteResponse(roleDto);
		return deleteRoleResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editRole(RoleDto roleDto, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateRoleServiceResponse = roleResponse.updateResponse(roleDto);
		return updateRoleServiceResponse;
	}
	
	@PUT
	@Path("/addaction")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAction(RoleDto roleIdWithActionsToAdd, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateRoleServiceResponse = roleResponse.addActionResponse(roleIdWithActionsToAdd);
		return updateRoleServiceResponse;
	}
	
	@PUT
	@Path("/removeaction")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeAction(RoleDto roleIdWithActionsToRemove, @HeaderParam("token") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateRoleServiceResponse = roleResponse.removeActionResponse(roleIdWithActionsToRemove);
		return updateRoleServiceResponse;
	}

}
