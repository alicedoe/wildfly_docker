package com.alicegabbana.cahierenligne.services.school;

import java.util.List;

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
	
	public Response getAll() {
		List<SchoolDto> schoolsDto = schoolService.getAll();
		return authService.returnResponse(200, schoolsDto);
	}
	
	public Response update(SchoolDto schoolDto) {
		
		try {
			SchoolDto schoolDtoUpdated = schoolService.update(schoolDto);
			return authService.returnResponse(200, schoolDtoUpdated);
			
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
	
	public Response delete(Long id) {
		
		try {
			schoolService.delete(id);
			return authService.returnResponse(200);
		} catch (SchoolException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}
	}
	
	public Response get(Long id) {
		try {
			SchoolDto schoolDto = schoolService.getDto(id);
			return authService.returnResponse(200, schoolDto);
		} catch (SchoolException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}
	}
}
