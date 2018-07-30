package com.alicegabbana.cahierenligne.services.school;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.SchoolDto;
import com.alicegabbana.cahierenligne.entities.School;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.town.TownException;

@Stateless
public class SchoolResponse {
	
	@EJB
	SchoolServiceLocal schoolService;
	
	@EJB
	AuthServiceLocal authService;
	
	public Response create(SchoolDto schoolDto) {
		
		try {
			School schoolCreated = schoolService.create(schoolDto);
			return authService.returnResponse(200, schoolCreated);
		} catch (SchoolException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		} catch (TownException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}			
		
	}
}
