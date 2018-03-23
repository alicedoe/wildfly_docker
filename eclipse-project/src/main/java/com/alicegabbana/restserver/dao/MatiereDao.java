package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Matiere;

@Stateless
public class MatiereDao {
	
	Logger logger = Logger.getLogger(MatiereDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Matiere getMatiereById (Long id) {
		
		TypedQuery<Matiere> query_name = em.createQuery("SELECT matiere FROM Matiere matiere WHERE matiere.id = :id", Matiere.class)
				.setParameter("id", id);
		List<Matiere> loadedMatieres = query_name.getResultList();

		if ( loadedMatieres.size() != 0 ) {
			return loadedMatieres.get(0);
		}
		logger.info("Dao get : matiere with this id doesn't exist");		
		return null;
	}
	
	public Matiere getMatiereByName (String name) {
		
		TypedQuery<Matiere> query_name = em.createQuery("SELECT matiere FROM Matiere matiere WHERE matiere.nom = :name", Matiere.class)
				.setParameter("name", name);
		List<Matiere> loadedMatieres = query_name.getResultList();
		
		if ( loadedMatieres.size() != 0 ) {
			return loadedMatieres.get(0);
		}
		logger.info("Dao get : matiere with this name doesn't exist");			
		return null;
	}
	
	public List<Matiere> getAllMatieres () {
		
		TypedQuery<Matiere> query_matieres = em.createQuery("SELECT matiere FROM Matiere matiere", Matiere.class);
		List<Matiere> loadedMatieres = query_matieres.getResultList();
		
		if ( loadedMatieres.size() != 0 ) {
			return loadedMatieres;
		}
		logger.info("Dao get : there is no matiere");			
		return null;
	}

}
