package com.alicegabbana.restserver.service;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dto.ActionDto;
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
	
	public Response createResponse(ActionDto newActionDto) {
		
		if ( newActionDto == null || newActionDto.getId() != null || newActionDto.getName() == "" )
			return authService.returnResponse(400);
		if ( nameExistService(newActionDto.getName()) == true ) return authService.returnResponse(409);
		
		Action actionCreated = createService(newActionDto);		
		return authService.returnResponseWithEntity(201, actionCreated);

	}
	
	public Response updateResponse(ActionDto actionDtoToUpdate) {
		
		if ( actionDtoToUpdate == null || actionDtoToUpdate.getId() == null || actionDtoToUpdate.getName() == "" )
			return authService.returnResponse(400);

		if ( nameExistService(actionDtoToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Action action = getByIdService(actionDtoToUpdate.getId());
		if (action.getName() == actionDtoToUpdate.getName()) return authService.returnResponse(200);
		
		Action updatedAction = updateService(actionDtoToUpdate);
		
		return authService.returnResponseWithEntity(200, updatedAction);

	}
	
	public Response deleteResponse (ActionDto actionDto) {
		
		if ( actionDto.getId() == null ) return authService.returnResponse(400);
		Action action = getByIdService(actionDto.getId());
		if ( action == null ) return authService.returnResponse(404);
		deleteService(actionDto.getId());		
		return authService.returnResponse(200);

	}
	
	public Response getResponse ( ActionDto actionDto ) {
		
		Action action = getByIdService(actionDto.getId());
		if ( action == null ) return authService.returnResponse(404);
		
		return authService.returnResponseWithEntity(200, action);

	}
	
	public Response getAllResponse ( ) {

		List<Action> loadedActions = getAllService();
		return authService.returnResponseWithEntity(200, loadedActions);

	}

// Services

	public Action createService( ActionDto newActionDto ) {
		Action actionTocreate = dtoToDao(newActionDto);
		Action actionCreated = em.merge(actionTocreate);
		return actionCreated;
	}	
	
	public ActionDto updateService( ActionDto actionDto ) {	
		Action actionToUpdate = dtoToDao(actionDto);
		Action updatedAction = em.merge(actionToUpdate);
		ActionDto actionDtoUpdated = daoToDto(updatedAction);
		return actionDtoUpdated;
	}
	
	public ActionDto daoToDto (Action action) {
		
		ActionDto actionDto = new ActionDto();
		if (action != null) {
			actionDto.setId(action.getId());
			actionDto.setName(action.getName());
		}
		
		return actionDto;
	}
	
	public Action dtoToDao (ActionDto actionDto) {
		
		Action action = new Action();
		if (actionDto != null) {
			action.setId(actionDto.getId());
			action.setName(actionDto.getName());
		}
		
		return action;
	}
	
	public List<ActionDto> daoListToDtoList (List<Action> actionList) {

		List<ActionDto> actionDtoList = new ArrayList<ActionDto>();
		for (Action action : actionList) {
			ActionDto actionDto = daoToDto(action);
			actionDtoList.add(actionDto);
		}

		return actionDtoList;
	}
	
	public void deleteService( Long id) {		
		Action action = em.find(Action.class, id);
		em.remove(action);
	}
	
	public List<Action> getAllService () {		
		List<Action> loadedActions = actionDao.getAllActions();
		return loadedActions;
	}
	
	public boolean nameExistService(String name) {
		
		if (actionDao.getActionByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Action getByIdService (Long id) {
		return actionDao.getActionById(id);
	}
	
	public Action getByName (String name) {
		return actionDao.getActionByName(name);
	}
	
}
