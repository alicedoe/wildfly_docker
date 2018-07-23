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
		if ( get(action.getName()) != null ) {
			logger.info("Action "+action.toString()+" already exist !");
			return action;
		} else {
			Action actionCreated = em.merge(action);
			return actionCreated;
		}
	}
	
	public Action get(String name) {
		Action action = em.find(Action.class, name);
		if (action == null) {
			logger.info("Action "+name+" Not found !");	
		}
		return action;
	}
}
