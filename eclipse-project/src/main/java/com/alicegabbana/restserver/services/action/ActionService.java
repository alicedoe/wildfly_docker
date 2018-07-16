package com.alicegabbana.restserver.services.action;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.entity.Action;

@Stateless
public class ActionService implements ActionServiceRemote, ActionServiceLocal {
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

//	@Override
//	public Action create( Action action ) {
////		Action actionCreated = em.merge(action);
//		return action;
//	}
	
	public Action test( Action action ) {
		System.out.println("test");
		Action actionCreated = em.merge(action);
		return actionCreated;
	}

}
