package com.alicegabbana.restserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.model.User;
import com.alicegabbana.restserver.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@EJB
	AuthService authService;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public User create( User user ) {		
		
		if ( emailExist(user.getEmail()) ) {
			return null;
		}
		
		try {
			String token = authService.createToken(user.getEmail());
			user.setToken(token);
		} catch (KeyLengthException e) {
			e.printStackTrace();
			return null;
		} catch (JOSEException e) {
			e.printStackTrace();
			return null;
		}
		
		User loadedUser = em.merge(user);
		return loadedUser;
		
	}
	
	public boolean emailExist(String email) {
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", email);
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return true;
		}
				
		return false;
	}
	
}
