package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Niveau;

@Stateless
public class NiveauDao {
	
	Logger logger = Logger.getLogger(NiveauDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Niveau create( Niveau niveau ) {		
		
		if ( niveau.getId() != null ) {
			String message = "Id must be null for Role creation : " + niveau;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( niveauExist(niveau) == true ) {
			return null;
		}
		
		Niveau loadedNiveau = em.merge(niveau);
		return loadedNiveau;
		
	}
	
	public boolean niveauExist (Niveau niveau) {
		
		TypedQuery<Niveau> query_name = em.createQuery("SELECT niveau FROM Role niveau WHERE niveau.nom = :name", Niveau.class)
				.setParameter("name", niveau.getNom());
		List<Niveau> loadedNiveaus = query_name.getResultList();
		
		if ( loadedNiveaus.size() != 0 ) {
			return true;
		}
				
		return false;
	}

}
