package com.alicegabbana.cahierenligne.services.town;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.entities.Town;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;

@Stateless
public class TownResponse {
	
	@EJB
	TownServiceLocal townService;
	
	@EJB
	AuthServiceLocal authService;

	public Response create(String name) {		
		try {
			Town townCreated = townService.create(name);
			return authService.returnResponse(200, townCreated);
		} catch (TownException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}		
	}
	
	public Response delete(Long id) {		
		try {
			townService.delete(id);
			return authService.returnResponse(200);
		} catch (TownException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}		
	}
	
	public Response update(Town town) {		
		try {
			Town updatedTown = townService.update(town);
			return authService.returnResponse(200, updatedTown);
		} catch (TownException e) {			
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}		
	}
	
	public Response getAll() {
		return authService.returnResponse(200, townService.getAllTowns());
	}
	
	public Response get(Long id) {
		try {
			return authService.returnResponse(200, townService.get(id));
		} catch (TownException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}
	}
}
