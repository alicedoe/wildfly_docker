package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.modelDao.Kidsclass;

@Stateless
public class ClasseDao {
	
	Logger logger = Logger.getLogger(ClasseDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Kidsclass create( Kidsclass classe ) {		
		
		if ( classe.getId() != null ) {
			String message = "Id must be null for Role creation : " + classe;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( classeExist(classe) == true ) {
			return null;
		}
		
		Kidsclass loadedClasse = em.merge(classe);
		return loadedClasse;
		
	}
	
	public boolean classeExist (Kidsclass classe) {
		
		TypedQuery<Kidsclass> query_name = em.createQuery("SELECT classe FROM Role classe WHERE classe.nom = :name", Kidsclass.class)
				.setParameter("name", classe.getNom());
		List<Kidsclass> loadedClasses = query_name.getResultList();
		
		if ( loadedClasses.size() != 0 ) {
			return true;
		}
				
		return false;
	}

}
