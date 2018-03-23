package com.alicegabbana.restserver.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.MatiereDao;
import com.alicegabbana.restserver.entity.Matiere;

@Stateless
public class MatiereService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	MatiereDao matiereDao;
	
	Logger logger = Logger.getLogger(MatiereService.class);
	
	public Response createMatiere(Matiere newMatiere) {
		
		if ( newMatiere == null || newMatiere.getId() != null || newMatiere.getNom() == "" ) return authService.returnResponse(400);

		if ( matiereNameExist(newMatiere.getNom()) == true ) return authService.returnResponse(409);
		
		Matiere matiereCreated = em.merge(newMatiere);
		return authService.returnResponseWithEntity(201, matiereCreated);

	}
	
	public Response updateMatiere(Matiere matiereToUpdate) {
		
		if ( matiereToUpdate == null || matiereToUpdate.getId() == null || matiereToUpdate.getNom() == "" ) return authService.returnResponse(400);

		if ( matiereNameExist(matiereToUpdate.getNom()) == true ) return authService.returnResponse(409);
		
		Matiere matiere = getMatiereById(matiereToUpdate.getId());
		if (matiere.getNom() == matiereToUpdate.getNom()) return authService.returnResponse(200);
		
		Matiere updatedMatiere = em.merge(matiereToUpdate);
		return authService.returnResponseWithEntity(200, updatedMatiere);

	}
	
	public Response deleteMatiere (Matiere matiere) {

		if ( matiere == null || matiere.getId() == null ) return authService.returnResponse(400);
		
		if ( matiereNameExist(matiere.getNom()) == false ) return authService.returnResponse(404);

		matiere = em.find(Matiere.class, matiere.getId());
		em.remove(matiere);
		return authService.returnResponse(200);

	}
	
	public Response getMatiere ( Long id ) {

		Matiere matiere = em.find(Matiere.class, id);
		return authService.returnResponseWithEntity(200, matiere);

	}
	
	public Response getAllMatiere ( ) {

		List<Matiere> loadedMatieres = matiereDao.getAllMatieres();
		
		if ( loadedMatieres == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedMatieres);

	}
	
	public boolean matiereNameExist(String name) {
		
		if (matiereDao.getMatiereByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Matiere getMatiereById (Long id) {
		return matiereDao.getMatiereById(id);
	}
	
	public Matiere getMatiereByName (String name) {
		return matiereDao.getMatiereByName(name);
	}
	
}
