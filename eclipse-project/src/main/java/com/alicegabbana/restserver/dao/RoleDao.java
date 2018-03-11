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

	public Role create( Role role ) {		
		
		if ( role.getId() != null ) {
			String message = "Id must be null for Role creation : " + role;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
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
			return true;
		}
				
		return false;
	}

}
