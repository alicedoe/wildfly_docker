package com.alicegabbana.restserver.services.role;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class RoleResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	RoleDao roleDao;
	
	@EJB
	RoleService roleService;
	
	Logger logger = Logger.getLogger(RoleResponse.class);
	
	public Response create(RoleDto roleDto) {		
		
		if (roleDto.getId() != null) return authService.returnResponse(400);
		if ( roleService.nameExist(roleDto.getName()) ) return authService.returnResponse(409);		
		
		RoleDto roleCreated = roleService.createService(roleDto);
		return authService.returnResponseWithEntity(201, roleCreated);
		
	}
	
	public Response delete(RoleDto roleDto) {
		
		if ( roleDto.getId() == null ) return authService.returnResponse(400);		
		if ( roleService.getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);

		roleService.deleteService(roleDto.getId());
		return authService.returnResponse(200);
	}
	
	public Response update (RoleDto roleDto) {
		
		if ( roleDto == null || roleDto.getId() == null ) return authService.returnResponse(400);
		if ( roleService.nameExist(roleDto.getName()) == false ) return authService.returnResponse(404);
		
		RoleDto roleDtoUpdated = roleService.updateService(roleDto);
		return authService.returnResponseWithEntity(200, roleDtoUpdated);

	}
	
	public Response getById(RoleDto roleDtoToGet) {
		
		if ( roleDtoToGet == null ) return authService.returnResponse(400);		
		if ( roleService.getDaoByIdService(roleDtoToGet.getId()) == null ) return authService.returnResponse(404);
		
		Role role = roleService.getDaoByIdService(roleDtoToGet.getId());
		RoleDto roleDto = roleService.daoToDto(role);
		return authService.returnResponseWithEntity(200, roleDto);
		
	}
	
	public Response getAllRole() {		
		List<RoleDto> roleList = roleService.getAllService();
		return authService.returnResponseWithEntity(200, roleList);
		
	}
	
	public Response getActionFromRoleResponse(RoleDto roleDto) {		
		
		if ( roleService.getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, roleService.getActionService(roleDto));
	}
}
