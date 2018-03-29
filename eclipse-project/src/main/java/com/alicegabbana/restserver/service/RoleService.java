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
	
///////////////	
//Response //
///////////////	
	
	public Response createResponse(RoleDto roleDto) {		
		
		if (roleDto.getId() != null) return authService.returnResponse(400);
		if ( nameExist(roleDto.getName()) ) return authService.returnResponse(409);		
		
		return authService.returnResponseWithEntity(201, createService(roleDto));
		
	}
	
	public Response deleteResponse(Long roleId) {
		
		if ( roleId == null ) return authService.returnResponse(400);		
		if ( getDaoByIdService(roleId) == null ) return authService.returnResponse(404);

		deleteService(roleId);
		return authService.returnResponse(200);
	}
	
	public Response updateResponse (RoleDto roleDto) {
		
		if ( roleDto == null || roleDto.getId() == null ) return authService.returnResponse(400);
		if ( nameExist(roleDto.getName()) == false ) return authService.returnResponse(404);
		
		RoleDto roleDtoUpdated = updateService(roleDto);
		return authService.returnResponseWithEntity(200, roleDtoUpdated);

	}
	
	public Response addActionResponse(Long roleId, List<Long> actionsIdList) {
		
		Role role = getDaoByIdService(roleId);
		
		if ( roleId == null || actionsIdList == null || actionsIdList.size() == 0 ) 
			return authService.returnResponse(400);
		if ( role == null ) return authService.returnResponse(404);		
				
		RoleDto roleDto = addActionService(role.getId(), actionsIdList);		
		return authService.returnResponseWithEntity(200, roleDto);
	}
	
	public Response getByIdResponse(Long roleId) {
		
		if ( roleId == null ) return authService.returnResponse(400);		
		if ( getDaoByIdService(roleId) == null ) return authService.returnResponse(404);
		
		Role role = getDaoByIdService(roleId);
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
	
	public RoleDto addActionService ( Long roleId, List<Long> actionsIdList ) {
		Role role = getDaoByIdService(roleId);
		final List<Action> actionsList = new ArrayList<Action>();
		
		actionsIdList.forEach(new Consumer<Long>() {
			public void accept(Long actionID) {
				Action action = actionService.getByIdService(actionID);
				actionsList.add(action);
			}
		});
		
		role.setActions(actionsList);
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
		
		List<RoleDto> roleDtoList = daoListToDtoList(loadedRoles);
		
		return roleDtoList;
	}
	
	public List<Action> getActionService ( RoleDto roleDto) {
		
		List<Action> actionList = roleDao.getActionFromRole(roleDto.getId());
		return actionList;
	}
}
