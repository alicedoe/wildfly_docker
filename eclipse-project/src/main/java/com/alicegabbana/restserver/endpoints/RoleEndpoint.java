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
		
		Response addRoleServiceResponse = roleResponse.create(roleDto);
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
		
		Response getRoleServiceResponse = roleResponse.getById(roleDto);
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
		
		Response getAllRoleServiceResponse = roleResponse.getAllRole();
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
		
		Response deleteRoleResponse = roleResponse.delete(roleDto);
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
		
		Response updateRoleServiceResponse = roleResponse.update(roleDto);
		return updateRoleServiceResponse;
	}

}
