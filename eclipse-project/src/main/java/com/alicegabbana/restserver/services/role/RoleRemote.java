package com.alicegabbana.restserver.services.role;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.restserver.entity.Role;

@Remote
public interface RoleRemote extends Serializable {

	Role create (Role role);
}
