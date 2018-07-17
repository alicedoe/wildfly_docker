package com.alicegabbana.restserver.services.action;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alicegabbana.restserver.entity.Action;

@Stateless
public class ActionService implements ActionServiceRemote {
	
	private static final long serialVersionUID = 7002507505754920295L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	

	public Action create( Action action ) {
		Action actionCreated = em.merge(action);
		return actionCreated;
	}
	
	public Action updateService( Action action ) {	
		Action updatedAction = em.merge(action);
		return updatedAction;
	}

	public void deleteService( Long id) {		
		Action action = em.find(Action.class, id);
		em.remove(action);
	}
}
