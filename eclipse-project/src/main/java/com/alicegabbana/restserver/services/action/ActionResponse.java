package com.alicegabbana.restserver.services.action;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dto.ActionDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class ActionResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	ActionService actionService;
	
	@EJB
	ActionDao actionDao;
	
	Logger logger = Logger.getLogger(ActionResponse.class);
	
	public Response createResponse(ActionDto newActionDto) {
		
		if ( newActionDto == null || newActionDto.getId() != null || newActionDto.getName().equals("") )
			return authService.returnResponse(400);
		if ( actionService.nameExistService(newActionDto.getName()) == true ) return authService.returnResponse(409);
		
		Action actionCreated = actionService.createService(newActionDto);		
		return authService.returnResponseWithEntity(201, actionCreated);

	}
	
	public Response updateResponse(ActionDto actionDtoToUpdate) {
		
		if ( actionDtoToUpdate == null || actionDtoToUpdate.getId() == null || actionDtoToUpdate.getName().equals("") )
			return authService.returnResponse(400);

		if ( actionService.nameExistService(actionDtoToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Action action = actionService.getByIdService(actionDtoToUpdate.getId());
		if (action.getName() == actionDtoToUpdate.getName()) return authService.returnResponse(200);
		
		Action updatedAction = actionService.updateService(actionDtoToUpdate);
		
		return authService.returnResponseWithEntity(200, updatedAction);

	}
	
	public Response deleteResponse (ActionDto actionDto) {
		
		if ( actionDto.getId() == null ) return authService.returnResponse(400);
		Action action = actionService.getByIdService(actionDto.getId());
		if ( action == null ) return authService.returnResponse(404);
		actionService.deleteService(actionDto.getId());		
		return authService.returnResponse(200);

	}
	
	public Response getResponse ( ActionDto actionDto ) {
		
		Action action = actionService.getByIdService(actionDto.getId());
		if ( action == null ) return authService.returnResponse(404);
		
		return authService.returnResponseWithEntity(200, action);

	}
	
	public Response getAllResponse ( ) {

		List<Action> loadedActions = actionService.getAllService();
		return authService.returnResponseWithEntity(200, loadedActions);

	}
	
}
