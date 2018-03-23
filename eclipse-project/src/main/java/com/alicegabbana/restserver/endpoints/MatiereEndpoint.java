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

import com.alicegabbana.restserver.entity.Matiere;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.MatiereService;

@Path("/matiere")
public class MatiereEndpoint {
	
	@EJB
	MatiereService matiereService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(MatiereEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMatiere(Matiere matiere, @HeaderParam("UserToken") String userToken) {
		
		List<String> matieresNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create matiere"
	            		));
		if (authService.userHasActionList(userToken, matieresNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response addMatiereServiceResponse = matiereService.createMatiere(matiere);
		return addMatiereServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Long id, @HeaderParam("UserToken") String userToken) {

		List<String> matieresNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read matiere"
	            		));		
		if (authService.userHasActionList(userToken, matieresNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getMatiereServiceResponse = matiereService.getMatiere(id);
		return getMatiereServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMatiere(@HeaderParam("UserToken") String userToken) {

		List<String> matieresNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read matiere"
	            		));
		if (authService.userHasActionList(userToken, matieresNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response getAllMatiereServiceResponse = matiereService.getAllMatiere();
		return getAllMatiereServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMatiere(Matiere matiere, @HeaderParam("UserToken") String userToken) {

		List<String> matieresNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete matiere"
	            		));
		if (authService.userHasActionList(userToken, matieresNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response deleteMatiereResponse = matiereService.deleteMatiere(matiere);
		return deleteMatiereResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editMatiere(Matiere matiere, @HeaderParam("UserToken") String userToken) {

		List<String> matieresNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update matiere"
	            		));
		if (authService.userHasActionList(userToken, matieresNeeded) == false ) 
		{
			return authService.returnResponse(401);
		}
		
		Response updateMatiereServiceResponse = matiereService.updateMatiere(matiere);
		return updateMatiereServiceResponse;
	}

}
