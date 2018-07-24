package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;

import net.minidev.json.JSONObject;

@Local
public interface UserServiceLocal {
	User getByToken (String token);
	boolean emailFormatCorrect(String email);
	User getByEmail ( String email ) throws UserException;
	UserDto daoToDto(User user);
	boolean isPasswordCorrect(String email, String password);
	UserDto login (JSONObject body) throws UserException;
}
