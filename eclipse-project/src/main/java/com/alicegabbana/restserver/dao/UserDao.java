package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

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

}
