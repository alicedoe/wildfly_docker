package com.alicegabbana.cahierenligne.services.role;

import java.util.List;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.RoleDto;
import com.alicegabbana.cahierenligne.entities.Role;

@Local
public interface RoleServiceLocal {
	Role get(String name) throws RoleException;
	List<RoleDto> getAll();
	RoleDto daoToDto(Role role);
}
