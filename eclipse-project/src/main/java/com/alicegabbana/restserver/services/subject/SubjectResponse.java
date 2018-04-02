package com.alicegabbana.restserver.services.subject;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.SubjectDao;
import com.alicegabbana.restserver.dto.SubjectDto;
import com.alicegabbana.restserver.entity.Subject;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class SubjectResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	SubjectService subjectService;
	
	Logger logger = Logger.getLogger(SubjectResponse.class);
	
	public Response createResponse(Subject newSubject) {
		
		if ( newSubject == null || newSubject.getId() != null || newSubject.getName().equals("") ) return authService.returnResponse(400);

		if ( subjectService.subjectNameExist(newSubject.getName()) == true ) return authService.returnResponse(409);
		
		Subject subjectCreated = em.merge(newSubject);
		return authService.returnResponseWithEntity(201, subjectCreated);

	}
	
	public Response updateResponse(SubjectDto subjectDtoToUpdate) {
		
		if ( subjectDtoToUpdate == null || subjectDtoToUpdate.getId() == null || subjectDtoToUpdate.getName().equals("") ) return authService.returnResponse(400);

		if ( subjectService.subjectNameExist(subjectDtoToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		SubjectDto subjectDtoUpdated = subjectService.update(subjectDtoToUpdate);
		return authService.returnResponseWithEntity(200, subjectDtoUpdated);

	}
	
	public Response deleteResponse(SubjectDto subjectDto) {

		if ( subjectDto == null || subjectDto.getId() == null ) return authService.returnResponse(400);
		
		if ( subjectService.getDaoById(subjectDto.getId()) == null ) return authService.returnResponse(404);
		
		subjectService.delete(subjectDto.getId());
		return authService.returnResponse(200);

	}
	
	public Response getResponse( SubjectDto subjectDto ) {
		
		if ( subjectDto == null || subjectDto.getId() == null ) return authService.returnResponse(400);
		
		if ( subjectService.getDaoById(subjectDto.getId()) == null ) return authService.returnResponse(404);

		SubjectDto subject = subjectService.getDtoById(subjectDto.getId());
		
		return authService.returnResponseWithEntity(200, subject);

	}
	
	public Response getAllResponse( ) {

		List<SubjectDto> subjectDtoList = subjectService.getAllDto();	

		return authService.returnResponseWithEntity(200, subjectDtoList);

	}
	
}
