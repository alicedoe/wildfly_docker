package com.alicegabbana.restserver.endpoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.HomeworkDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.HomeworkService;

@Path("/homework")
public class HomeworkEndpoint {
	
	@EJB
	HomeworkService homeService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(HomeworkEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addHomework(HomeworkDto homeworkDto, @HeaderParam("UserToken") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create homework"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addHomeworkServiceResponse = homeService.createResponse(homeworkDto);
		return addHomeworkServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRole(@HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read homework"
	            		));
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllHomeworkServiceResponse = homeService.getAllResponse();
		return getAllHomeworkServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(HomeworkDto homeworkDto, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read homework"
	            		));		
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getHomeworkServiceResponse = homeService.getResponse(homeworkDto);
		return getHomeworkServiceResponse;
	}
	
	@GET
	@Path("/getforkidsclass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(KidsClass kidsClass, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read homework"
	            		));		
		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getHomeworkServiceResponse = homeService.getForKidsClassResponse(kidsClass);
		return getHomeworkServiceResponse;
	}
//	
//	
//	@GET
//	@Path("/getaction")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAction(RoleDto roleDto, @HeaderParam("UserToken") String userToken) {
//
//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"read role"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
//		
//		Response getActionFromRoleServiceResponse = roleService.getActionFromRole(roleDto);
//		return getActionFromRoleServiceResponse;
//	}
//	
//	@DELETE
//	@Path("/delete")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response deleteRole(RoleDto roleDto, @HeaderParam("UserToken") String userToken) {
//
//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"delete role"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
//		
//		Response deleteRoleResponse = roleService.deleteResponse(roleDto);
//		return deleteRoleResponse;
//	}
//	
//	@PUT
//	@Path("/edit")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response editRole(RoleDto roleDto, @HeaderParam("UserToken") String userToken) {
//
//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"update role"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
//		
//		Response updateRoleServiceResponse = roleService.updateResponse(roleDto);
//		return updateRoleServiceResponse;
//	}
//	
//	@PUT
//	@Path("/addaction")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response addAction(RoleDto roleIdWithActionsToAdd, @HeaderParam("UserToken") String userToken) {
//
//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"update role"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
//		
//		Response updateRoleServiceResponse = roleService.addActionResponse(roleIdWithActionsToAdd);
//		return updateRoleServiceResponse;
//	}
//	
//	@PUT
//	@Path("/removeaction")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response removeAction(RoleDto roleIdWithActionsToRemove, @HeaderParam("UserToken") String userToken) {
//
//		List<String> actionsNeeded = new ArrayList<String>(
//	            Arrays.asList(
//	            		"update role"
//	            		));
//		if (authService.userHasActionList(userToken, actionsNeeded) == false ) 
//		{
//			return authService.returnResponse(401);
//		}
//		
//		Response updateRoleServiceResponse = roleService.removeActionResponse(roleIdWithActionsToRemove);
//		return updateRoleServiceResponse;
//	}

}
