package com.alicegabbana.restserver.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.resterver.model.template.Book;

import javax.ejb.Stateless;

@Stateless
public class UserDao {
	
	Logger logger = Logger.getLogger(UserDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Book create( ) {		
		
		Book book = new Book("mon super titre","auteur",12);
		book.setId(2);
		Book loadedBook = em.merge(book);
		return loadedBook;
		
	}
}
