package com.alicegabbana.restserver.services.action;

import javax.ejb.Stateless;

@Stateless
public class ActionpService {
	
//	@PersistenceContext(unitName = "MariadbConnexion")
//	EntityManager em;
//	
//	@EJB
//	ActionDao actionDao;
//	
//	Logger logger = Logger.getLogger(ActionService.class);
//
//	public Action create( Action action ) {
//		Action actionCreated = em.merge(action);
//		return actionCreated;
//	}	
//	
//	public Action updateService( Action action ) {	
//		Action updatedAction = em.merge(action);
//		return updatedAction;
//	}
//	
//	public void deleteService( Long id) {		
//		Action action = em.find(Action.class, id);
//		em.remove(action);
//	}
//	
//	public List<Action> getAllService () {		
//		List<Action> loadedActions = actionDao.getAllActions();
//		return loadedActions;
//	}
//	
//	public boolean nameExistService(String name) {
//		
//		if (actionDao.getActionByName(name) == null) {
//			return false;
//		}
//		
//		return true;
//	}
//	
//	public Action getByIdService (Long id) {
//		return actionDao.getActionById(id);
//	}
//	
//	public Action getByName (String name) {
//		return actionDao.getActionByName(name);
//	}
	
}
