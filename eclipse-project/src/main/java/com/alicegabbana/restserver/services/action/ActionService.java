package com.alicegabbana.restserver.services.action;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dto.ActionDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class ActionService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	ActionDao actionDao;
	
	Logger logger = Logger.getLogger(ActionService.class);

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
