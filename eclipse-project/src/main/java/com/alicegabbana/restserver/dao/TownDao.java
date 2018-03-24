package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Town;

@Stateless
public class TownDao {
	
	Logger logger = Logger.getLogger(TownDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Town getTownById (Long id) {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.id = :id", Town.class)
				.setParameter("id", id);
		List<Town> loadedTowns = query_name.getResultList();

		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		logger.info("Dao get : town with this id doesn't exist");		
		return null;
	}
	
	public Town getTownByName (String name) {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.name = :name", Town.class)
				.setParameter("name", name);
		List<Town> loadedTowns = query_name.getResultList();
		
		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		logger.info("Dao get : town with this name doesn't exist");			
		return null;
	}
	
	public List<Town> getAllTowns () {
		
		TypedQuery<Town> query_towns = em.createQuery("SELECT town FROM Town town", Town.class);
		List<Town> loadedTowns = query_towns.getResultList();
		
		if ( loadedTowns.size() != 0 ) {
			return loadedTowns;
		}
		logger.info("Dao get : there is no town");			
		return null;
	}

}
