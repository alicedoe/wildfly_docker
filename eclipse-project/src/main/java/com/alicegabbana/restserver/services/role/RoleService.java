package com.alicegabbana.restserver.services.role;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.action.ActionService;

@Stateless
public class RoleService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	ActionService actionService;
	
	@EJB
	RoleDao roleDao;
	
	Logger logger = Logger.getLogger(RoleService.class);
	
	public boolean nameExist (String name) {
		
		if ( roleDao.getRoleByName(name) == null ) return false;
		return true;
	}
	
	public RoleDto daoToDto (Role role) {
		
		RoleDto roleDto = new RoleDto();
		if (role != null) {
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
			if (role.getActions() != null) {
				final List<Long> actionsList = new ArrayList<Long>();
				role.getActions().forEach(new Consumer<Action>() {
					public void accept(Action action) {
						actionsList.add(action.getId());
					}
				});
				roleDto.setActions(actionsList);	
			}
		}
		
		return roleDto;
	}
	
	public Role dtoToDao (RoleDto roleDto) {
		
		Role role = new Role();
		if (roleDto != null) {
			if (roleDto.getId() != null) role.setId(roleDto.getId());
			if (roleDto.getName() != null) role.setName(roleDto.getName());
		}
		return role;
	}
	
	public List<RoleDto> daoListToDtoList (List<Role> roleList) {

		List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
		for (Role role : roleList) {
			RoleDto roleDto = daoToDto(role);
			roleDtoList.add(roleDto);
		}

		return roleDtoList;
	}
	
	public RoleDto createService ( RoleDto roleDto) {
		Role role = dtoToDao(roleDto);
		Role roleCreated = em.merge(role);
		RoleDto roleDtoCreated = daoToDto(roleCreated);
		return roleDtoCreated;
	}
	
	public void deleteService ( Long roleId) {
		Role role = em.find(Role.class, roleId);
		em.remove(role);
	}
	
	public RoleDto updateService ( RoleDto roleDto) {
		Role roleToUpdate = getDaoByIdService(roleDto.getId()); 
		roleToUpdate.setName(roleDto.getName());
		Role updatedRole = em.merge(roleToUpdate);
		RoleDto roleDtoUpdated = daoToDto(updatedRole);
		return roleDtoUpdated;
	}
	
	public RoleDto addActionService ( Long roleId, List<Long> actionsIdToAdd ) {
		Role role = getDaoByIdService(roleId);
		
		final List<Action> actionsList = role.getActions();
		
		actionsIdToAdd.forEach(new Consumer<Long>() {
			public void accept(Long actionID) {
				Action action = actionService.getByIdService(actionID);
				if ( !actionsList.contains(action)) actionsList.add(action);
			}
		});
		
		role.setActions(actionsList);
		Role roleUpdated = em.merge(role);
		return daoToDto(roleUpdated);
	}
	
	public RoleDto removeActionService ( Long roleId, List<Long> actionsIdToremove ) {
		Role role = getDaoByIdService(roleId);
		final List<Action> roleActionsList = role.getActions();
		
		actionsIdToremove.forEach(new Consumer<Long>() {
			public void accept(Long actionID) {
				Action action = actionService.getByIdService(actionID);
				if ( roleActionsList.contains(action)) roleActionsList.remove(action);
			}
		});
		
		role.setActions(roleActionsList);
		Role roleUpdated = em.merge(role);
		return daoToDto(roleUpdated);
	}
	
	public Role getDaoByIdService (Long roleId) {
		Role role = roleDao.getRoleById(roleId);
		return role;
	}
	
	public Role getDaoByName (String name) {
		return roleDao.getRoleByName(name);
	}
	
	public List<RoleDto> getAllService () {
		List<Role> loadedRoles = roleDao.getAllRoles();
		if ( loadedRoles != null) {
			List<RoleDto> roleDtoList = daoListToDtoList(loadedRoles);
			return roleDtoList;
		}
		return null;
	}
	
	public List<Action> getActionService ( RoleDto roleDto) {
		
		List<Action> actionList = roleDao.getActionFromRole(roleDto.getId());
		return actionList;
	}
}
