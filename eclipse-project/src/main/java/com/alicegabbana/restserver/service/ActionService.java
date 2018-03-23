package com.alicegabbana.restserver.service;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.Role;

@Stateless
public class ActionService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	ActionDao actionDao;
	
	Logger logger = Logger.getLogger(ActionService.class);
	
	public Response createAction(Action newAction) {
		
		if ( newAction == null || newAction.getId() != null || newAction.getNom() == "" ) return authService.returnResponse(400);

		if ( actionNameExist(newAction.getNom()) == true ) return authService.returnResponse(409);
		
		Action actionCreated = em.merge(newAction);
		return authService.returnResponseWithEntity(201, actionCreated);

	}
	
	public Response updateAction(Action actionToUpdate) {
		
		if ( actionToUpdate == null || actionToUpdate.getId() == null || actionToUpdate.getNom() == "" ) return authService.returnResponse(400);

		if ( actionNameExist(actionToUpdate.getNom()) == true ) return authService.returnResponse(409);
		
		Action action = getActionById(actionToUpdate.getId());
		if (action.getNom() == actionToUpdate.getNom()) return authService.returnResponse(200);
		
		Action updatedAction = em.merge(actionToUpdate);
		return authService.returnResponseWithEntity(200, updatedAction);

	}
	
	public Response deleteAction (Action action) {

		if ( action == null || action.getId() == null ) return authService.returnResponse(400);
		
		if ( actionNameExist(action.getNom()) == false ) return authService.returnResponse(404);

		action = em.find(Action.class, action.getId());
		em.remove(action);
		return authService.returnResponse(200);

	}
	
	public Response getAction ( Long id ) {

		Action action = em.find(Action.class, id);
		return authService.returnResponseWithEntity(200, action);

	}
	
	public Response getAllAction ( ) {

		List<Action> loadedActions = actionDao.getAllActions();
		
		if ( loadedActions == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedActions);

	}
	
	public boolean actionNameExist(String name) {
		
		if (actionDao.getActionByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Action getActionById (Long id) {
		return actionDao.getActionById(id);
	}
	
	public Action getActionByName (String name) {
		return actionDao.getActionByName(name);
	}
	
}
