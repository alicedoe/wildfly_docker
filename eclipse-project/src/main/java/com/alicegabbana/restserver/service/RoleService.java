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

import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.modelDao.Role;
import com.alicegabbana.restserver.modelDao.User;
import com.alicegabbana.restserver.modelDto.RoleDto;
import com.alicegabbana.restserver.modelDto.UserDto;

@Stateless
public class RoleService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	RoleDao roleDao;
	
	Logger logger = Logger.getLogger(RoleService.class);
	
	public Response createRole(String userToken, List<String> actionsNeeded, RoleDto roleDto) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if (roleDto.getId() != null) return builder.build();
		
		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();

		if ( roleNameExist(roleDto.getName()) ) return builder.status(Response.Status.CONFLICT).build();
		else {			
			Role role = roleDtoToRole(roleDto);
			Role roleCreated = em.merge(role);
			builder.status(Response.Status.OK);
			builder.entity(roleCreated);
		}	
		
		return builder.build();
	}
	
	public Response deleteRole(String userToken, List<String> actionsNeeded, Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			builder.status(Response.Status.UNAUTHORIZED);
		else {
			if ( roleNameExist(role.getName()) == false ) builder.status(Response.Status.NOT_FOUND);
			else {
				role = em.find(Role.class, role.getId());
				em.remove(role);
				builder.status(Response.Status.OK);
			}
		}
		
		return builder.build();
	}
	
	public Response updateRole(String userToken, List<String> actionsNeeded, Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			builder.status(Response.Status.UNAUTHORIZED);
		else {
		if ( roleNameExist(role.getName()) == false ) builder.status(Response.Status.NOT_FOUND);
			else {
				Role updatedRole = em.merge(role);
				builder.entity(updatedRole);
				builder.status(Response.Status.OK);
			}
		}
		
		return builder.build();
	}
	
	public Response getRole(String userToken, List<String> actionsNeeded, Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( role == null || role.getId() == null ) return builder.build();
		
		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			builder.status(Response.Status.UNAUTHORIZED);
		else {
			if ( !roleNameExist(role.getName()) ) builder.status(Response.Status.NOT_FOUND);
			else {
				role = em.find(Role.class, role.getId());
				builder.entity(role);
				builder.status(Response.Status.OK);
			}
		}
		
		return builder.build();
	}
	
	public Response getAllRole(String userToken, List<String> actionsNeeded) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		List<Role> loadedRoles = roleDao.getAllRoles();
				
		if ( loadedRoles == null ) builder.status(Response.Status.NOT_FOUND);		
		else {
			List<RoleDto> roleDtoList = roleListToRoleDtoList(loadedRoles);
			builder.entity(roleDtoList);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public boolean roleNameExist (String name) {
		
		if ( roleDao.get(name) == null ) return false;
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
}
