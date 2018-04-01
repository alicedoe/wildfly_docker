package com.alicegabbana.restserver.service;

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
	
	public Response createResponse(RoleDto roleDto) {		
		
		if (roleDto.getId() != null) return authService.returnResponse(400);
		if ( nameExist(roleDto.getName()) ) return authService.returnResponse(409);		
		
		RoleDto roleCreated = createService(roleDto);
		return authService.returnResponseWithEntity(201, roleCreated);
		
	}
	
	public Response deleteResponse(RoleDto roleDto) {
		
		if ( roleDto.getId() == null ) return authService.returnResponse(400);		
		if ( getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);

		deleteService(roleDto.getId());
		return authService.returnResponse(200);
	}
	
	public Response updateResponse (RoleDto roleDto) {
		
		if ( roleDto == null || roleDto.getId() == null ) return authService.returnResponse(400);
		if ( nameExist(roleDto.getName()) == false ) return authService.returnResponse(404);
		
		RoleDto roleDtoUpdated = updateService(roleDto);
		return authService.returnResponseWithEntity(200, roleDtoUpdated);

	}
	
	public Response addActionResponse(RoleDto roleDto) {
		
		Role role = getDaoByIdService(roleDto.getId());
		if ( role == null ) return authService.returnResponse(404);
		if ( role.getId() == null || roleDto.getActions() == null || roleDto.getActions().size() == 0 ) 
			return authService.returnResponse(400);		
				
		RoleDto roleDtoUpdated = addActionService(role.getId(), roleDto.getActions());		
		return authService.returnResponseWithEntity(200, roleDtoUpdated);
	}
	
	public Response removeActionResponse(RoleDto roleDto) {
		
		Role role = getDaoByIdService(roleDto.getId());
		
		if ( role == null ) return authService.returnResponse(404);
		if ( role.getId() == null || roleDto.getActions() == null || roleDto.getActions().size() == 0 ) 
			return authService.returnResponse(400);				
				
		RoleDto roleDtoUpdated = removeActionService(role.getId(), roleDto.getActions());		
		return authService.returnResponseWithEntity(200, roleDtoUpdated);
	}
	
	public Response getByIdResponse(RoleDto roleDtoToGet) {
		
		if ( roleDtoToGet == null ) return authService.returnResponse(400);		
		if ( getDaoByIdService(roleDtoToGet.getId()) == null ) return authService.returnResponse(404);
		
		Role role = getDaoByIdService(roleDtoToGet.getId());
		RoleDto roleDto = daoToDto(role);
		return authService.returnResponseWithEntity(200, roleDto);
		
	}
	
	public Response getAllRole() {		
		
		return authService.returnResponseWithEntity(200, getAllService());
		
	}
	
	public Response getActionFromRole(RoleDto roleDto) {		
		
		if ( getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, getActionService(roleDto));
	}
	
	public boolean nameExist (String name) {
		
		if ( roleDao.getRoleByName(name) == null ) return false;
		return true;
	}
	
	public RoleDto daoToDto (Role role) {
		
		RoleDto roleDto = new RoleDto();
		if (role != null) {
			roleDto.setId(role.getId());
			roleDto.setName(role.getName());
			final List<Long> actionsList = new ArrayList<Long>();
			role.getActions().forEach(new Consumer<Action>() {
				public void accept(Action action) {
					actionsList.add(action.getId());
				}
			});
			roleDto.setActions(actionsList);			
		}
		
		return roleDto;
	}
	
	public Role dtoToDao (RoleDto roleDto) {
		
		Role role = new Role();
		if (roleDto != null) {
			role.setId(roleDto.getId());
			role.setName(roleDto.getName());
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
