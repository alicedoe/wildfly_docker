package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.Role;

@Stateless
public class ActionDao {
	
	Logger logger = Logger.getLogger(ActionDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Action create( Action action ) {		
		
		if ( action.getId() != null ) {
			String message = "Id must be null for Role creation : " + action;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( getAction(action) != null ) {
			return null;
		}
		
		Action loadedAction = em.merge(action);
		return loadedAction;
		
	}
	
	public Action getAction (Action action) {
		
		TypedQuery<Action> query_name = em.createQuery("SELECT action FROM Action action WHERE action.nom = :name", Action.class)
				.setParameter("name", action.getNom());
		List<Action> loadedActions = query_name.getResultList();
		logger.error(loadedActions.size());
		if ( loadedActions.size() != 0 ) {
			return loadedActions.get(0);
		}
				
		return null;
	}

}
