package com.alicegabbana.restserver.services.homework;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.HomeworkDao;
import com.alicegabbana.restserver.dto.HomeworkDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.kidsclass.KidsClassService;
import com.alicegabbana.restserver.services.subject.SubjectService;
import com.alicegabbana.restserver.services.user.UserService;

@Stateless
public class HomeworkResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	SubjectService subjectService;
	
	@EJB
	UserService userService;
	
	@EJB
	HomeworkService homeworkService;
	
	@EJB
	HomeworkDao homeworkDao;
	
	Logger logger = Logger.getLogger(HomeworkResponse.class);
	
	public Response create(HomeworkDto homeworkDto) {		
		
		if (homeworkDto.getId() != null || !homeworkService.homeworkDtoIsComplete(homeworkDto, false) ) return authService.returnResponse(400);
		
		HomeworkDto homeworkCreated = homeworkService.createService(homeworkDto);
		return authService.returnResponseWithEntity(201, homeworkCreated);
		
	}
	
	public Response getAll ( ) {		
		return authService.returnResponseWithEntity(200, homeworkService.getAllService());
	}
	
	public Response get ( HomeworkDto homeworkDto ) {	
		
		if ( homeworkService.getService(homeworkDto.getId()) == null ) return authService.returnResponse(404);
		
		HomeworkDto homeworkDtoFound = homeworkService.getService(homeworkDto.getId());
		return authService.returnResponseWithEntity(200, homeworkDtoFound);
	}
	
	public Response getForKidsClass ( KidsClass kidsClass ) {	
		
		if ( kidsClassService.getById(kidsClass.getId()) == null ) return authService.returnResponse(404);
		
		List<HomeworkDto> homeworkDtoList = homeworkService.getForKidsClassService(kidsClass.getId());
		return authService.returnResponseWithEntity(200, homeworkDtoList);
	}
}
