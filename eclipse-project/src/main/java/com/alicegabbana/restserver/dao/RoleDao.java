package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.Role;

@Stateless
public class RoleDao {
	
	Logger logger = Logger.getLogger(RoleDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Role create (Role role) {
		Role roleCreated = em.merge(role);
		return roleCreated;
	}

	public Role getRoleById ( Long id ) {		
		
		Role role = em.find(Role.class, id);
		if (role != null) return role;
		logger.info("Dao getById : role with this id doesn't exist");	
		return null;
		
	}
	
	public Role getRoleByName ( String name ) {		
		
		TypedQuery<Role> query_name = em.createQuery("SELECT role FROM Role role WHERE role.name = :name", Role.class)
				.setParameter("name", name);
		List<Role> loadedRole = query_name.getResultList();
		
		if ( loadedRole.size() != 0 ) {
			return loadedRole.get(0);
		}
		logger.info("Dao get : role with this name doesn't exist");				
		return null;
		
	}
	
	public List<Role> getAllRoles () {
		TypedQuery<Role> query_roles = em.createQuery("SELECT role FROM Role role", Role.class);
		List<Role> loadedRoles = query_roles.getResultList();
		
		return loadedRoles;
	}

	public List<Action> getActionFromRole (Long id) {
		@SuppressWarnings("unchecked")
		TypedQuery<Action> query_action = (TypedQuery<Action>) em.createQuery("SELECT role.actions FROM Role role WHERE role.id = :id")
				.setParameter("id", id);
		List<Action> loadedAction = query_action.getResultList();
		
		if ( loadedAction.size() != 0 ) {
			return loadedAction;
		}
		logger.info("Dao get : no action for this role");				
		return null;
	}
}
