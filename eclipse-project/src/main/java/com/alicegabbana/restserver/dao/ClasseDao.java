package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Classe;

@Stateless
public class ClasseDao {
	
	Logger logger = Logger.getLogger(ClasseDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Classe create( Classe classe ) {		
		
		if ( classe.getId() != null ) {
			String message = "Id must be null for Role creation : " + classe;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( classeExist(classe) == true ) {
			return null;
		}
		
		Classe loadedClasse = em.merge(classe);
		return loadedClasse;
		
	}
	
	public boolean classeExist (Classe classe) {
		
		TypedQuery<Classe> query_name = em.createQuery("SELECT classe FROM Role classe WHERE classe.nom = :name", Classe.class)
				.setParameter("name", classe.getNom());
		List<Classe> loadedClasses = query_name.getResultList();
		
		if ( loadedClasses.size() != 0 ) {
			return true;
		}
				
		return false;
	}

}
