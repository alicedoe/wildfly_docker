package com.alicegabbana.restserver.services.action;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class ActionResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
//	@EJB
//	ActionService actionService;
	
	@EJB
	ActionDao actionDao;
	
	Logger logger = Logger.getLogger(ActionResponse.class);
	
//	public Response getAll ( ) {
//
//		List<Action> loadedActions = actionService.getAllService();
//		return authService.returnResponseWithEntity(200, loadedActions);
//
//	}
	
}
