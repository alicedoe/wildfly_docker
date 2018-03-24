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
public class MatiereService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	SubjectDao matiereDao;
	
	Logger logger = Logger.getLogger(MatiereService.class);
	
	public Response createMatiere(Subject newMatiere) {
		
		if ( newMatiere == null || newMatiere.getId() != null || newMatiere.getName() == "" ) return authService.returnResponse(400);

		if ( matiereNameExist(newMatiere.getName()) == true ) return authService.returnResponse(409);
		
		Subject matiereCreated = em.merge(newMatiere);
		return authService.returnResponseWithEntity(201, matiereCreated);

	}
	
	public Response updateMatiere(Subject matiereToUpdate) {
		
		if ( matiereToUpdate == null || matiereToUpdate.getId() == null || matiereToUpdate.getName() == "" ) return authService.returnResponse(400);

		if ( matiereNameExist(matiereToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Subject matiere = getMatiereById(matiereToUpdate.getId());
		if (matiere.getName() == matiereToUpdate.getName()) return authService.returnResponse(200);
		
		Subject updatedMatiere = em.merge(matiereToUpdate);
		return authService.returnResponseWithEntity(200, updatedMatiere);

	}
	
	public Response deleteMatiere (Subject matiere) {

		if ( matiere == null || matiere.getId() == null ) return authService.returnResponse(400);
		
		if ( matiereNameExist(matiere.getName()) == false ) return authService.returnResponse(404);

		matiere = em.find(Subject.class, matiere.getId());
		em.remove(matiere);
		return authService.returnResponse(200);

	}
	
	public Response getMatiere ( Long id ) {

		Subject matiere = em.find(Subject.class, id);
		return authService.returnResponseWithEntity(200, matiere);

	}
	
	public Response getAllMatiere ( ) {

		List<Subject> loadedMatieres = matiereDao.getAllMatieres();
		
		if ( loadedMatieres == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedMatieres);

	}
	
	public boolean matiereNameExist(String name) {
		
		if (matiereDao.getMatiereByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Subject getMatiereById (Long id) {
		return matiereDao.getMatiereById(id);
	}
	
	public Subject getMatiereByName (String name) {
		return matiereDao.getMatiereByName(name);
	}
	
}
