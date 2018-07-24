package com.alicegabbana.cahierenligne.services.user;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.entities.User;

@Remote
public interface UserServiceRemote extends Serializable {

	User create(User user)  throws UserException;
}
