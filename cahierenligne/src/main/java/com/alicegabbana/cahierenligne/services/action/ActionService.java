package com.alicegabbana.cahierenligne.services.action;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alicegabbana.cahierenligne.entities.Action;

@Stateless
public class ActionService implements ActionServiceRemote {
	
	private static final long serialVersionUID = -2666467701541680725L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	

	public Action create( Action action ) {
		Action actionCreated = em.merge(action);
		return actionCreated;
	}
	
	public Action getAction (String name) {	
		Action action;
		try {
			action = em.find(Action.class, name);
		} catch (Exception e) {
			System.out.println("Entity not found");
			return null;
		}		
		return action;
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
