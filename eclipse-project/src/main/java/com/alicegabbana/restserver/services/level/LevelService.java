package com.alicegabbana.restserver.services.level;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.LevelDao;
import com.alicegabbana.restserver.entity.Level;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class LevelService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	LevelDao levelDao;
	
	Logger logger = Logger.getLogger(LevelService.class);
	
	public boolean levelNameExist(String name) {
		
		if (levelDao.getLevelByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public void delete (Long id) {
		
		Level level = getLevelById(id);
		em.remove(level);
	}
	
	public Level getLevelById (Long id) {
		return levelDao.getLevelById(id);
	}
	
	public Level getLevelByName (String name) {
		return levelDao.getLevelByName(name);
	}
	
	public List<Level> getAll () {
		return levelDao.getAllLevels();
	}
	
}
