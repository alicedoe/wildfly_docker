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
import com.alicegabbana.restserver.entity.User;

@Stateless
public class UserDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public User get( Long id ) {		
		
		User user = em.find(User.class, id);
		if (user != null) return user;
		logger.info("Dao getById : user with this id doesn't exist");	
		return null;
		
	}
	
	public User getByEmail ( String email ) {		
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", email);
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		logger.info("Dao getByEmail : user with this email doesn't exist");				
		return null;
		
	}
	
	public User getByToken (String token) {
		
		TypedQuery<User> query_token = em.createQuery("SELECT user FROM User user WHERE user.token = :token", User.class)
				.setParameter("token", token);
		List<User> loadedUsers = query_token.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		logger.info("Dao getByToken : user with this token doesn't exist");		
		return null;
	}
	
	public List<User> fetchAllUserDao () {
		List<User> userDetailsList = em.createQuery("SELECT user FROM User user", User.class)
				.getResultList();
		return userDetailsList;
	}
	
	public List<User> getUserFromKidsClass ( Long id ) {		
		TypedQuery<User> query_user = em.createQuery("SELECT user FROM User user WHERE user.kidsClass.id = :id ", User.class)
				.setParameter("id", id);
		List<User> loadedUsers = query_user.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers;
		}
		logger.info("Dao get : no user from kidsclass");				
		return null;
	}
	
	//TODO requête pourrie à revoir
	public List<Action> getActionFromUser ( Long id ) {
		
		logger.error("user id : "+id);
		TypedQuery<Role> query_role = em.createQuery("SELECT user.role FROM User user WHERE user.id = :id ", Role.class)
				.setParameter("id", id);
		List<Role> loadedRole = query_role.getResultList();
		
		if (loadedRole != null) {
			
			TypedQuery<Action> query_action = (TypedQuery<Action>) em.createQuery("SELECT role.actions FROM Role role WHERE role.id = :id")
					.setParameter("id", id);
			List<Action> loadedActions = query_action.getResultList();
			logger.error("quantité action : "+ loadedActions.size());
			if ( loadedActions.size() != 0 ) {
				return loadedActions;
			}
			logger.info("Dao get : no action from this user");				
			return null;
		}
		
		return null;
	}
	
	public List<User> getUserWithRole ( Long id ) {		
		TypedQuery<User> query_user = em.createQuery("SELECT user FROM User user WHERE user.role.id = :id ", User.class)
				.setParameter("id", id);
		List<User> loadedUsers = query_user.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers;
		}
		logger.info("Dao get : no user with this role");				
		return null;
	}

}
