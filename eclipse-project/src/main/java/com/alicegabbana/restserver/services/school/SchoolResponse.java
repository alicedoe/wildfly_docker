package com.alicegabbana.restserver.services.school;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.SchoolDao;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.entity.School;
import com.alicegabbana.restserver.entity.Town;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.action.ActionService;
import com.alicegabbana.restserver.services.town.TownService;

@Stateless
public class SchoolResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TownService townService;
	
	@EJB
	ActionService actionService;
	
	@EJB
	SchoolService schoolService;
	
	Logger logger = Logger.getLogger(SchoolResponse.class);
	
	public Response create(SchoolDto schoolDto) {		
		
		if (schoolDto.getId() != null) return authService.returnResponse(400);

		if ( schoolService.schoolNameExist(schoolDto) ) return authService.returnResponse(409);
		
		if ( !townService.nameExist(schoolDto.getTownName()) ) return authService.returnResponse(404);
		
		SchoolDto schoolDtoCreated = schoolService.createService(schoolDto);
		return authService.returnResponseWithEntity(201, schoolDtoCreated);
		
	}
	
	public Response delete(Long schoolId) {
		
		if ( schoolId == null ) return authService.returnResponse(400);
		
		if ( schoolService.getDtoByIdService(schoolId) == null ) return authService.returnResponse(404);
		
		schoolService.deleteService(schoolId);
		
		return authService.returnResponse(200);
	}
	
	public Response update(SchoolDto schoolDto) {
		
		if ( schoolDto == null || schoolDto.getId() == null || schoolDto.getName() == null || schoolDto.getTownName() == null ) return authService.returnResponse(400);

		if ( schoolService.schoolNameExist(schoolDto) == true ) return authService.returnResponse(409);
		
		if ( townService.nameExist(schoolDto.getTownName()) == false ) return authService.returnResponse(404);
		
		SchoolDto schoolDtoUpdated = schoolService.updateService(schoolDto);
		return authService.returnResponseWithEntity(200, schoolDtoUpdated);

	}
	
	public Response get(Long schoolId) {
		
		if ( schoolId == null ) return authService.returnResponse(400);
		
		if ( schoolService.getDtoByIdService(schoolId) == null ) return authService.returnResponse(404);
		
		SchoolDto schoolDtoFind = schoolService.getDtoByIdService(schoolId);
		return authService.returnResponseWithEntity(200, schoolDtoFind);
		
	}
	
	public Response getAll() {
		
		List<SchoolDto> schoolDtoList = schoolService.getAllService();
		return authService.returnResponseWithEntity(200, schoolDtoList);
		
	}
	
}
