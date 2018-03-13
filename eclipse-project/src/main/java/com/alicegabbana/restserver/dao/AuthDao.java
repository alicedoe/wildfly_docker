package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.User;

@Stateless
public class AuthDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public boolean userCanDoAction( String token, Action action ) {		
		
		User currentUser;
		
		TypedQuery<User> query_user = em.createQuery("SELECT user FROM User user WHERE user.token = :token", User.class)
				.setParameter("token", token);
		List<User> loadedUsers = query_user.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			currentUser = loadedUsers.get(0);
			logger.error("we are checkin user : "+currentUser.getNom());
			if ( !currentUser.getRole().getActions().contains(action) ) {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
		
	}
	
	public Action getActionByItsName( String name ) {
		
		Action currentAction;
		
		TypedQuery<Action> query_action = em.createQuery("SELECT action FROM Action action WHERE action.nom = :name", Action.class)
				.setParameter("name", name);
		List<Action> loadedActions = query_action.getResultList();
		
		if ( loadedActions.size() == 0 ) {
			return null;
		}
		
		currentAction = loadedActions.get(0);
		return currentAction;
		
	}
	
}
