package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.modelDao.Town;

@Stateless
public class VilleDao {
	
	Logger logger = Logger.getLogger(VilleDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Town create( Town ville ) {		
		
		if ( ville.getId() != null ) {
			String message = "Id must be null for Role creation : " + ville;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( villeExist(ville) == true ) {
			return null;
		}
		
		Town loadedVille = em.merge(ville);
		return loadedVille;
		
	}
	
	public boolean villeExist (Town ville) {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT ville FROM Role ville WHERE ville.nom = :name", Town.class)
				.setParameter("name", ville.getNom());
		List<Town> loadedVilles = query_name.getResultList();
		
		if ( loadedVilles.size() != 0 ) {
			return true;
		}
				
		return false;
	}

}
