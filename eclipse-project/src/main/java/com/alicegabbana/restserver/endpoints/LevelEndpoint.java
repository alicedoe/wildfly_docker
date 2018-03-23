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

import com.alicegabbana.restserver.entity.Level;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.LevelService;

@Path("/level")
public class LevelEndpoint {
	
	@EJB
	LevelService levelService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(LevelEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLevel(Level level, @HeaderParam("UserToken") String userToken) {
		
		List<String> levelsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create level"
	            		));
		if (authService.userHasActionList(userToken, levelsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addLevelServiceResponse = levelService.createLevel(level);
		return addLevelServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Long id, @HeaderParam("UserToken") String userToken) {

		List<String> levelsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read level"
	            		));		
		if (authService.userHasActionList(userToken, levelsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getLevelServiceResponse = levelService.getLevel(id);
		return getLevelServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLevel(@HeaderParam("UserToken") String userToken) {

		List<String> levelsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read level"
	            		));
		if (authService.userHasActionList(userToken, levelsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllLevelServiceResponse = levelService.getAllLevel();
		return getAllLevelServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteLevel(Level level, @HeaderParam("UserToken") String userToken) {

		List<String> levelsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete level"
	            		));
		if (authService.userHasActionList(userToken, levelsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteLevelResponse = levelService.deleteLevel(level);
		return deleteLevelResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editLevel(Level level, @HeaderParam("UserToken") String userToken) {

		List<String> levelsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update level"
	            		));
		if (authService.userHasActionList(userToken, levelsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateLevelServiceResponse = levelService.updateLevel(level);
		return updateLevelServiceResponse;
	}

}
