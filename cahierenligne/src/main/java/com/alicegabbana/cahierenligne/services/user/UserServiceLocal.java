package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;

import net.minidev.json.JSONObject;

@Local
public interface UserServiceLocal {
	UserDto login (JSONObject body) throws UserException;
	UserDto create(NewUserDto newUserDto) throws UserException;
	User getByToken (String token);
}
