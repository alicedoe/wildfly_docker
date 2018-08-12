package com.alicegabbana.cahierenligne.services.role;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.RoleDto;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;

@Stateless
public class RoleResponse {
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	RoleServiceLocal roleService;
	
	public Response getAll() {
		List<RoleDto> allRole =  roleService.getAll();
		return authService.returnResponse(200, allRole);
	}
}
