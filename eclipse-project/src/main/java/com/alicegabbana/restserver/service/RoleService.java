package com.alicegabbana.restserver.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;

@Stateless
public class RoleService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(RoleService.class);
	
	public Response createRole(Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if (role.getId() != null) role.setId(null);
		if ( roleExist(role) ) builder.status(Response.Status.CONFLICT);
		else {
			Role newRole = em.merge(role);
			builder.status(Response.Status.OK);
			builder.entity(newRole);
		}
		
		return builder.build();
	}
	
	public Response deleteRole(Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if ( !roleExist(role) ) builder.status(Response.Status.NOT_FOUND);
		else {
			role = em.find(Role.class, role.getId());
			em.remove(role);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public Response updateRole(Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if ( !roleExist(role) ) builder.status(Response.Status.NOT_FOUND);
		else {
			Role updatedRole = em.merge(role);
			builder.entity(updatedRole);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public Response getRole(Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if ( !roleExist(role) ) builder.status(Response.Status.NOT_FOUND);
		else {
			role = em.find(Role.class, role.getId());
			builder.entity(role);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public Response getAllRole() {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		TypedQuery<Role> query_name = em.createQuery("SELECT role FROM Role role", Role.class);
		List<Role> loadedRoles = query_name.getResultList();
		
		if ( loadedRoles == null ) builder.status(Response.Status.NOT_FOUND);
		else {
			builder.entity(loadedRoles);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public boolean roleExist (Role role) {
		
		TypedQuery<Role> query_name = em.createQuery("SELECT role FROM Role role WHERE role.id = :id", Role.class)
				.setParameter("id", role.getId());
		List<Role> loadedRoles = query_name.getResultList();
		
		if ( loadedRoles.size() != 0 ) {
			logger.info("Dao createRole : role already exist");	
			return true;
		}
		logger.info("Dao createRole : role doesn't exist");		
		return false;
	}
}
