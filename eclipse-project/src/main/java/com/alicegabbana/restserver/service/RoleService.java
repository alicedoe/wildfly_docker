package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.model.Role;

@Stateless
public class RoleService {
	
	@EJB
	RoleDao roleDao;
	
	Logger logger = Logger.getLogger(RoleService.class);
	
	public Role createRole(Role role) {
		
		if (role.getId() != null) role.setId(null);
		Role newRole = roleDao.createRole(role);
		
		return newRole;
	}
}
