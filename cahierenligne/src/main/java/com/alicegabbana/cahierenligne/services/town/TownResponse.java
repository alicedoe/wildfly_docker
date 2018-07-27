package com.alicegabbana.cahierenligne.services.town;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
			return authService.returnResponseWithEntity(200, townCreated);
		} catch (TownException e) {
			return authService.returnResponse(409);
		}		
	}
	
	public Response delete(Long id) {		
		try {
			townService.delete(id);
			return authService.returnResponse(200);
		} catch (TownException e) {
			return authService.returnResponse(404);
		}		
	}
	
	public Response update(Town town) {		
		try {
			Town updatedTown = townService.update(town);
			return authService.returnResponseWithEntity(200, updatedTown);
		} catch (TownException e) {
			return authService.returnResponse(400);
		}		
	}
	
	public Response getAll() {
		return authService.returnResponseWithEntity(200, townService.getAllTowns());
	}
}
