package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Action;

@Stateless
public class ActionDao {
	
	Logger logger = Logger.getLogger(ActionDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Action create (Action action) {
		Action actionCreated = em.merge(action);
		return actionCreated;
	}
	
	public Action update (Action action) {
		Action actionUpdated = em.merge(action);
		return actionUpdated;
	}
	
	public void delete( Long id) {		
		Action action = em.find(Action.class, id);
		em.remove(action);
	}
	
	public Action getActionById (Long id) {
		
		TypedQuery<Action> query_name = em.createQuery("SELECT action FROM Action action WHERE action.id = :id", Action.class)
				.setParameter("id", id);
		List<Action> loadedActions = query_name.getResultList();

		if ( loadedActions.size() != 0 ) {
			return loadedActions.get(0);
		}
		logger.info("Dao get : action with this id doesn't exist");		
		return null;
	}
	
	public Action getActionByName (String name) {
		
		TypedQuery<Action> query_name = em.createQuery("SELECT action FROM Action action WHERE action.name = :name", Action.class)
				.setParameter("name", name);
		List<Action> loadedActions = query_name.getResultList();
		
		if ( loadedActions.size() != 0 ) {
			return loadedActions.get(0);
		}
		logger.info("Dao get : action with this name doesn't exist");			
		return null;
	}
	
	public List<Action> getAllActions () {
		
		TypedQuery<Action> query_actions = em.createQuery("SELECT action FROM Action action", Action.class);
		List<Action> loadedActions = query_actions.getResultList();
		
		if ( loadedActions.size() != 0 ) {
			return loadedActions;
		}
		logger.info("Dao get : there is no action");			
		return null;
	}

}
