package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.User;

@Stateless
public class AuthDao {
	
	@EJB
	UserDao userDao;
	
	Logger logger = Logger.getLogger(AuthDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public boolean userHasThisAction( String token, Action action ) {		
		
		User currentUser;
		
		currentUser = userDao.getByToken(token);
		
		if ( currentUser != null ) {
			logger.info("userCanDoAction : user "+currentUser.getName());
			if ( !currentUser.getRole().getActions().contains(action) ) {
				return false;
			}
		} else {
			logger.info("userCanDoAction : return false");
			return false;
		}
		logger.info("userCanDoAction : return true");
		return true;
		
	}
	
}
