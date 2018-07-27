package com.alicegabbana.cahierenligne.services.action;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.entities.Action;

@Stateless
public class ActionService implements ActionServiceRemote, ActionServiceLocal {
	
	private static final long serialVersionUID = -2666467701541680725L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(ActionService.class);

	public Action create( Action action ) {
		
		try {
			get(action.getName());
			throw new ActionException("Action "+action.toString()+" already exist !");
		} catch (Exception e) {
			Action actionCreated = em.merge(action);
			return actionCreated;
		}
	}
	
	public Action get(String name) throws ActionException {
		Action action = em.find(Action.class, name);
		if (action == null) {
			throw new ActionException("Action "+name+" does not exist !");
		}
		return action;
	}
}
