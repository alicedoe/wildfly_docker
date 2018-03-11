package com.alicegabbana.restserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.model.User;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class UserDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public User create( User user ) {		
		
		if ( user.getId() != null ) {
			String message = "Id must be null for Role creation : " + user;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( userExist(user) == true ) {
			return null;
		}
		
		User loadedUser = em.merge(user);
		return loadedUser;
		
	}
	
	public boolean userExist (User user) {
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", user.getEmail());
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return true;
		}
				
		return false;
	}
	
}
