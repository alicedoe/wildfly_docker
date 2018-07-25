package com.alicegabbana.cahierenligne.services.role;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.entities.Role;

@Remote
public interface RoleServiceRemote extends Serializable {

	Role create (Role role) throws RoleException;
	Role get(String name) throws RoleException;
}
