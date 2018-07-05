package com.alicegabbana.restserver.services.level;

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
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class LevelResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	LevelService levelService;
	
	@EJB
	LevelDao levelDao;
	
	Logger logger = Logger.getLogger(LevelResponse.class);
	
	public Response create(Level newLevel) {
		
		if ( newLevel == null || newLevel.getId() != null || newLevel.getName().equals("") ) return authService.returnResponse(400);

		if ( levelService.levelNameExist(newLevel.getName()) == true ) return authService.returnResponse(409);
		
		Level levelCreated = em.merge(newLevel);
		return authService.returnResponseWithEntity(201, levelCreated);

	}
	
	public Response update(Level levelToUpdate) {
		
		if ( levelToUpdate == null || levelToUpdate.getId() == null || levelToUpdate.getName().equals("") || levelToUpdate.getName() == null ) return authService.returnResponse(400);
		
		if ( levelService.levelNameExist(levelToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Level updatedLevel = em.merge(levelToUpdate);
		return authService.returnResponseWithEntity(200, updatedLevel);

	}
	
	public Response delete (Level level) {

		if ( level == null || level.getId() == null ) return authService.returnResponse(400);
		
		if ( levelService.getLevelById(level.getId()) == null ) return authService.returnResponse(404);
		
		levelService.delete(level.getId());
		return authService.returnResponse(200);

	}
	
	public Response get ( LevelDto levelDto ) {

		
		if ( levelDto == null || levelDto.getId() == null ) return authService.returnResponse(404);
		
		Level level = levelService.getLevelById(levelDto.getId());
		return authService.returnResponseWithEntity(200, level);

	}
	
	public Response getAll ( ) {

		List<Level> loadedLevels = levelService.getAll();
		return authService.returnResponseWithEntity(200, loadedLevels);

	}
	
}
