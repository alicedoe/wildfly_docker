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

import com.alicegabbana.restserver.entity.Town;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.TownService;

@Path("/town")
public class TownEndpoint {
	
	@EJB
	TownService townService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(TownEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTown(Town town, @HeaderParam("UserToken") String userToken) {
		
		List<String> townsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create town"
	            		));
		if (authService.userHasActionList(userToken, townsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addTownServiceResponse = townService.createTown(town);
		return addTownServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Long id, @HeaderParam("UserToken") String userToken) {

		List<String> townsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read town"
	            		));		
		if (authService.userHasActionList(userToken, townsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getTownServiceResponse = townService.getTown(id);
		return getTownServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTown(@HeaderParam("UserToken") String userToken) {

		List<String> townsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read town"
	            		));
		if (authService.userHasActionList(userToken, townsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllTownServiceResponse = townService.getAllTown();
		return getAllTownServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteTown(Town town, @HeaderParam("UserToken") String userToken) {

		List<String> townsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete town"
	            		));
		if (authService.userHasActionList(userToken, townsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteTownResponse = townService.deleteTown(town);
		return deleteTownResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editTown(Town town, @HeaderParam("UserToken") String userToken) {

		List<String> townsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update town"
	            		));
		if (authService.userHasActionList(userToken, townsNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateTownServiceResponse = townService.updateTown(town);
		return updateTownServiceResponse;
	}

}
