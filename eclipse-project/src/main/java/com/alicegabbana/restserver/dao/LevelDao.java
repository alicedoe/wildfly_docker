package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Level;

@Stateless
public class LevelDao {
	
	Logger logger = Logger.getLogger(LevelDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Level getLevelById (Long id) {
		
		TypedQuery<Level> query_name = em.createQuery("SELECT level FROM Level level WHERE level.id = :id", Level.class)
				.setParameter("id", id);
		List<Level> loadedLevels = query_name.getResultList();

		if ( loadedLevels.size() != 0 ) {
			return loadedLevels.get(0);
		}
		logger.info("Dao get : level with this id doesn't exist");		
		return null;
	}
	
	public Level getLevelByName (String name) {
		
		TypedQuery<Level> query_name = em.createQuery("SELECT level FROM Level level WHERE level.nom = :name", Level.class)
				.setParameter("name", name);
		List<Level> loadedLevels = query_name.getResultList();
		
		if ( loadedLevels.size() != 0 ) {
			return loadedLevels.get(0);
		}
		logger.info("Dao get : level with this name doesn't exist");			
		return null;
	}
	
	public List<Level> getAllLevels () {
		
		TypedQuery<Level> query_levels = em.createQuery("SELECT level FROM Level level", Level.class);
		List<Level> loadedLevels = query_levels.getResultList();
		
		if ( loadedLevels.size() != 0 ) {
			return loadedLevels;
		}
		logger.info("Dao get : there is no level");			
		return null;
	}

}
