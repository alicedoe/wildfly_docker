package com.alicegabbana.restserver.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.LevelDao;
import com.alicegabbana.restserver.dto.LevelDto;
import com.alicegabbana.restserver.entity.Level;

@Stateless
public class LevelService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	LevelDao levelDao;
	
	Logger logger = Logger.getLogger(LevelService.class);
	
	public Response createLevel(Level newLevel) {
		
		if ( newLevel == null || newLevel.getId() != null || newLevel.getName() == "" ) return authService.returnResponse(400);

		if ( levelNameExist(newLevel.getName()) == true ) return authService.returnResponse(409);
		
		Level levelCreated = em.merge(newLevel);
		return authService.returnResponseWithEntity(201, levelCreated);

	}
	
	public Response updateLevel(Level levelToUpdate) {
		
		if ( levelToUpdate == null || levelToUpdate.getId() == null || levelToUpdate.getName() == "" || levelToUpdate.getName() == null ) return authService.returnResponse(400);
		
		if ( levelNameExist(levelToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Level updatedLevel = em.merge(levelToUpdate);
		return authService.returnResponseWithEntity(200, updatedLevel);

	}
	
	public Response deleteLevel (Level level) {

		if ( level == null || level.getId() == null ) return authService.returnResponse(400);
		
		level = em.find(Level.class, level.getId());
		
		if ( level == null ) return authService.returnResponse(404);
		
		em.remove(level);
		return authService.returnResponse(200);

	}
	
	public Response getLevel ( LevelDto levelDto ) {

		Level level = em.find(Level.class, levelDto.getId());
		if ( level == null ) return authService.returnResponse(404);
		return authService.returnResponseWithEntity(200, level);

	}
	
	public Response getAllLevel ( ) {

		List<Level> loadedLevels = levelDao.getAllLevels();
		return authService.returnResponseWithEntity(200, loadedLevels);

	}
	
	public boolean levelNameExist(String name) {
		
		if (levelDao.getLevelByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Level getLevelById (Long id) {
		return levelDao.getLevelById(id);
	}
	
	public Level getLevelByName (String name) {
		return levelDao.getLevelByName(name);
	}
	
}
