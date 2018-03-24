package com.alicegabbana.restserver.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.TownDao;
import com.alicegabbana.restserver.entity.Town;

@Stateless
public class TownService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TownDao townDao;
	
	Logger logger = Logger.getLogger(TownService.class);
	
	public Response createTown(Town newTown) {
		
		if ( newTown == null || newTown.getId() != null || newTown.getName() == "" ) return authService.returnResponse(400);

		if ( townNameExist(newTown.getName()) == true ) return authService.returnResponse(409);
		
		Town townCreated = em.merge(newTown);
		return authService.returnResponseWithEntity(201, townCreated);

	}
	
	public Response updateTown(Town townToUpdate) {
		
		if ( townToUpdate == null || townToUpdate.getId() == null || townToUpdate.getName() == "" ) return authService.returnResponse(400);

		if ( townNameExist(townToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Town town = getTownById(townToUpdate.getId());
		if (town.getName() == townToUpdate.getName()) return authService.returnResponse(200);
		
		Town updatedTown = em.merge(townToUpdate);
		return authService.returnResponseWithEntity(200, updatedTown);

	}
	
	public Response deleteTown (Town town) {

		if ( town == null || town.getId() == null ) return authService.returnResponse(400);
		
		if ( townNameExist(town.getName()) == false ) return authService.returnResponse(404);

		town = em.find(Town.class, town.getId());
		em.remove(town);
		return authService.returnResponse(200);

	}
	
	public Response getTown ( Long id ) {

		Town town = em.find(Town.class, id);
		return authService.returnResponseWithEntity(200, town);

	}
	
	public Response getAllTown ( ) {

		List<Town> loadedTowns = townDao.getAllTowns();
		
		if ( loadedTowns == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedTowns);

	}
	
	public boolean townNameExist(String name) {
		
		if (townDao.getTownByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Town getTownById (Long id) {
		return townDao.getTownById(id);
	}
	
	public Town getTownByName (String name) {
		return townDao.getTownByName(name);
	}
	
}
