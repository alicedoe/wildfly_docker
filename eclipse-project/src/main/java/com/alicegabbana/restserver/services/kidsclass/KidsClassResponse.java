package com.alicegabbana.restserver.services.kidsclass;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.LevelDto;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class KidsClassResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(KidsClassResponse.class);
	
	public Response createResponse ( KidsClassDto kidsClassDto) {
		
		if ( kidsClassService.kidsClassIsCorrect(kidsClassDto, false) == false) return authService.returnResponse(400);

		if ( kidsClassService.kidsClassDao.kidsClassNameFromLevelExist(kidsClassDto.getName(), kidsClassDto.getLevelName()))
			return authService.returnResponse(409);
		
		KidsClassDto kidsClassDtoCreated = kidsClassService.createService(kidsClassDto);
		return authService.returnResponseWithEntity(201, kidsClassDtoCreated);
	}
	
	public Response getResponse ( KidsClassDto kidsClassDto ) {
		
		if (kidsClassDto.getId() == null ) return authService.returnResponse(400);

		if ( kidsClassService.getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, kidsClassService.getService(kidsClassDto.getId()));
	}
	
	public Response getAllResponse ( ) {
		
		
		return authService.returnResponseWithEntity(200, kidsClassService.getAllService());
	}
	
	public Response deleteResponse(Long kidsClassId) {
		
		if ( kidsClassId == null ) return authService.returnResponse(400);
		
		if ( kidsClassService.getById(kidsClassId) == null ) return authService.returnResponse(404);

		kidsClassService.deleteService(kidsClassId);
		return authService.returnResponse(200);
	}
	
	public Response updateResponse (KidsClassDto kidsClassDto) {
		
		if ( kidsClassDto == null || kidsClassDto.getId() == null || !kidsClassService.kidsClassIsCorrect(kidsClassDto, true) ) 
			return authService.returnResponse(400);

		if ( kidsClassService.getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);
		
		KidsClassDto kidsClassDtoUpdated = kidsClassService.updateService(kidsClassDto);
		
		return authService.returnResponseWithEntity(200, kidsClassDtoUpdated);

	}
	
	public Response getFromSchoolResponse (SchoolDto schoolDto) {
		
		if ( schoolDto == null || schoolDto.getId() == null ) return authService.returnResponse(400);
		
		if ( kidsClassService.schoolService.getDtoByIdService(schoolDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<KidsClassDto> kidsClassDtoList = kidsClassService.getFromSchoolService(schoolDto.getId());
		return authService.returnResponseWithEntity(200, kidsClassDtoList);
		
	}
	
	public Response getWithLevelResponse (LevelDto levelDto) {
		
		if ( levelDto == null || levelDto.getId() == null ) return authService.returnResponse(400);
		
		if ( kidsClassService.levelService.getLevelById(levelDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<KidsClassDto> kidsClassDtoList = kidsClassService.getWithLevelService(levelDto.getId());
		return authService.returnResponseWithEntity(200, kidsClassDtoList);
		
	}
}
