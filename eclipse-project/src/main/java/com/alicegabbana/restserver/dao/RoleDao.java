package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;

@Stateless
public class RoleDao {
	
	Logger logger = Logger.getLogger(RoleDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Role createRole ( Role role ) {		
		
		logger.info("Dao createRole : check if role exist");
		if ( roleExist(role) == true ) {
			return null;
		}
		
		Role loadedRole = em.merge(role);
		return loadedRole;
		
	}
	
	public boolean roleExist (Role role) {
		
		TypedQuery<Role> query_name = em.createQuery("SELECT role FROM Role role WHERE role.nom = :name", Role.class)
				.setParameter("name", role.getNom());
		List<Role> loadedRoles = query_name.getResultList();
		
		if ( loadedRoles.size() != 0 ) {
			logger.info("Dao createRole : role already exist");	
			return true;
		}
		logger.info("Dao createRole : role doesn't exist");		
		return false;
	}

}
