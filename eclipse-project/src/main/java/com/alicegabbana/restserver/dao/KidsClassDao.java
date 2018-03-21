package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Role;

@Stateless
public class KidsClassDao {
	
	Logger logger = Logger.getLogger(KidsClassDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public KidsClass getKidsClassById (Long id) {
		
		TypedQuery<KidsClass> query_id = em.createQuery("SELECT kidsClass FROM KidsClass kidsClass WHERE kidsClass.id = :id", KidsClass.class)
				.setParameter("id", id);
		List<KidsClass> loadedKidsClass = query_id.getResultList();

		if ( loadedKidsClass.size() != 0 ) {
			return loadedKidsClass.get(0);
		}
		logger.info("Dao get : KidsClass with this id doesn't exist");		
		return null;
	}
	
	public KidsClass getKidsClassByName (String name) {
		
		TypedQuery<KidsClass> query_name = em.createQuery("SELECT kidsClass FROM KidsClass kidsClass WHERE kidsClass.nom = :name", KidsClass.class)
				.setParameter("name", name);
		List<KidsClass> loadedKidsClass = query_name.getResultList();

		if ( loadedKidsClass.size() != 0 ) {
			return loadedKidsClass.get(0);
		}
		logger.info("Dao get : KidsClass with this name doesn't exist");			
		return null;
	}
	
	public List<KidsClass> getAllKidsClass () {
		
		TypedQuery<KidsClass> query_kidsClass = em.createQuery("SELECT action FROM Action action", KidsClass.class);
		List<KidsClass> loadedActions = query_kidsClass.getResultList();
		
		if ( loadedActions.size() != 0 ) {
			return loadedActions;
		}
		logger.info("Dao get : there is no kidsClass");			
		return null;
	}

}
