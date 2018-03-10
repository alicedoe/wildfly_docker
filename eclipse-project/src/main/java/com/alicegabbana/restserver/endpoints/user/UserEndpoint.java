package com.alicegabbana.restserver.endpoints.user;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.alicegabbana.resterver.model.template.Book;
import com.alicegabbana.restserver.model.dao.UserDao;

@Path("/user")
public class UserEndpoint {
	
	@EJB
	UserDao userDao;
	
	Logger logger = Logger.getLogger(UserEndpoint.class);

	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public int addUser() {

		Book newBook = userDao.create();
		
		return newBook.getId();
	}

}
