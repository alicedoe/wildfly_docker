package com.alicegabbana.restserver.service;

import java.util.ArrayList;
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
import org.jboss.resteasy.annotations.Status;

import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.entity.User;

@Stateless
public class RoleService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	RoleDao roleDao;
	
	Logger logger = Logger.getLogger(RoleService.class);
	
	public Response createRole(RoleDto roleDto) {		
		
		if (roleDto.getId() != null) return authService.returnResponse(400);

		if ( roleNameExist(roleDto.getName()) ) return authService.returnResponse(409);
		
		Role role = roleDtoToRole(roleDto);
		Role roleCreated = em.merge(role);
		RoleDto roleDtoCreated = roleToRoleDto(roleCreated);
		return authService.returnResponseWithEntity(200, roleDtoCreated);
		
	}
	
	public Response deleteRole(Role role) {
		
		if ( role == null || role.getId() == null ) return authService.returnResponse(400);
		
		if ( roleNameExist(role.getName()) == false ) return authService.returnResponse(404);

		role = em.find(Role.class, role.getId());
		em.remove(role);
		return authService.returnResponse(200);
	}
	
	public Response updateRole(Role role) {
		
		if ( role == null || role.getId() == null ) return authService.returnResponse(400);

		if ( roleNameExist(role.getName()) == false ) return authService.returnResponse(404);

		Role updatedRole = em.merge(role);
		RoleDto roleDtoUpdated = roleToRoleDto(updatedRole);
		return authService.returnResponseWithEntity(200, roleDtoUpdated);

	}
	
	public Response getRole(Role role) {
		
		if ( role == null || role.getId() == null ) return authService.returnResponse(400);
		
		if ( !roleNameExist(role.getName()) ) return authService.returnResponse(404);
		
		role = em.find(Role.class, role.getId());
		RoleDto fetchRole = roleToRoleDto(role);
		return authService.returnResponseWithEntity(200, fetchRole);
		
	}
	
	public Response getAllRole() {
		
		List<Role> loadedRoles = roleDao.getAllRoles();
				
		if ( loadedRoles == null ) return authService.returnResponse(404);		

		List<RoleDto> roleDtoList = roleListToRoleDtoList(loadedRoles);
		return authService.returnResponseWithEntity(200, roleDtoList);
		
	}
	
	public boolean roleNameExist (String name) {
		
		if ( roleDao.getRoleByName(name) == null ) return false;
		return true;
	}
	
	public RoleDto roleToRoleDto (Role role) {
		
		RoleDto roleDto = new RoleDto();
		if (role != null) {
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
		}
		
		return roleDto;
	}
	
	public Role roleDtoToRole (RoleDto roleDto) {
		
		Role role = new Role();
		if (roleDto != null) {
			role.setId(roleDto.getId());
			role.setName(roleDto.getName());
		}
		
		return role;
	}
	
	public List<RoleDto> roleListToRoleDtoList (List<Role> roleList) {

		List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
		for (Role role : roleList) {
			RoleDto roleDto = roleToRoleDto(role);
			roleDtoList.add(roleDto);
		}

		return roleDtoList;
	}
	
	public Role getRoleByName (String name) {
		return roleDao.getRoleByName(name);
	}
	
}
