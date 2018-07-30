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
			return authService.returnResponse(200, townCreated);
		} catch (TownException e) {
			return authService.returnResponse(e.getCode());
		}		
	}
	
	public Response delete(Long id) {		
		try {
			townService.delete(id);
			return authService.returnResponse(200);
		} catch (TownException e) {
			return authService.returnResponse(e.getCode());
		}		
	}
	
	public Response update(Town town) {		
		try {
			Town updatedTown = townService.update(town);
			return authService.returnResponse(200, updatedTown);
		} catch (TownException e) {			
			return authService.returnResponse(e.getCode());
		}		
	}
	
	public Response getAll() {
		return authService.returnResponse(200, townService.getAllTowns());
	}
	
	public Response get(Long id) {
		try {
			return authService.returnResponse(200, townService.get(id));
		} catch (TownException e) {
			return authService.returnResponse(e.getCode());
		}
	}
}
