package com.alicegabbana.restserver.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.resterver.model.User;

import javax.ejb.Stateless;

@Stateless
public class UserDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public void create( ) {		
		
//		User book = new User();
//		User loadedBook = em.merge(book);
//		return loadedBook;
		
	}
}
