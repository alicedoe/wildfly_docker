package com.alicegabbana.restserver.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.SubjectDao;
import com.alicegabbana.restserver.entity.Subject;

@Stateless
public class SubjectService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	SubjectDao subjectDao;
	
	Logger logger = Logger.getLogger(SubjectService.class);
	
	public Response createSubject(Subject newSubject) {
		
		if ( newSubject == null || newSubject.getId() != null || newSubject.getName() == "" ) return authService.returnResponse(400);

		if ( subjectNameExist(newSubject.getName()) == true ) return authService.returnResponse(409);
		
		Subject subjectCreated = em.merge(newSubject);
		return authService.returnResponseWithEntity(201, subjectCreated);

	}
	
	public Response updateSubject(Subject subjectToUpdate) {
		
		if ( subjectToUpdate == null || subjectToUpdate.getId() == null || subjectToUpdate.getName() == "" ) return authService.returnResponse(400);

		if ( subjectNameExist(subjectToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Subject subject = getSubjectById(subjectToUpdate.getId());
		if (subject.getName() == subjectToUpdate.getName()) return authService.returnResponse(200);
		
		Subject updatedSubject = em.merge(subjectToUpdate);
		return authService.returnResponseWithEntity(200, updatedSubject);

	}
	
	public Response deleteSubject (Subject subject) {

		if ( subject == null || subject.getId() == null ) return authService.returnResponse(400);
		
		if ( subjectNameExist(subject.getName()) == false ) return authService.returnResponse(404);

		subject = em.find(Subject.class, subject.getId());
		em.remove(subject);
		return authService.returnResponse(200);

	}
	
	public Response getSubject ( Long id ) {

		Subject subject = em.find(Subject.class, id);
		return authService.returnResponseWithEntity(200, subject);

	}
	
	public Response getAllSubject ( ) {

		List<Subject> loadedSubjects = subjectDao.getAllSubjects();
		
		if ( loadedSubjects == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedSubjects);

	}
	
	public boolean subjectNameExist(String name) {
		
		if (subjectDao.getSubjectByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Subject getSubjectById (Long id) {
		return subjectDao.getSubjectById(id);
	}
	
	public Subject getSubjectByName (String name) {
		return subjectDao.getSubjectByName(name);
	}
	
}
