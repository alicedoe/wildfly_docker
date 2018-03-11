package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Ville;

@Stateless
public class VilleDao {
	
	Logger logger = Logger.getLogger(VilleDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Ville create( Ville ville ) {		
		
		if ( ville.getId() != null ) {
			String message = "Id must be null for Role creation : " + ville;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( villeExist(ville) == true ) {
			return null;
		}
		
		Ville loadedVille = em.merge(ville);
		return loadedVille;
		
	}
	
	public boolean villeExist (Ville ville) {
		
		TypedQuery<Ville> query_name = em.createQuery("SELECT ville FROM Role ville WHERE ville.nom = :name", Ville.class)
				.setParameter("name", ville.getNom());
		List<Ville> loadedVilles = query_name.getResultList();
		
		if ( loadedVilles.size() != 0 ) {
			return true;
		}
				
		return false;
	}

}
