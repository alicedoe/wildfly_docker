package com.alicegabbana.cahierenligne.services.role;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.entities.Role;

@Local
public interface RoleServiceLocal {
	Role get(String name) throws RoleException;
}
