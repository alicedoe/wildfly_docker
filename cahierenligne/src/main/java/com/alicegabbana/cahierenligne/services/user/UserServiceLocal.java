package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;

@Local
public interface UserServiceLocal {
	User getByToken (String token);
	boolean emailFormatCorrect(String email);
	User getByEmail ( String email );
	UserDto daoToDto(User user);
	boolean isPasswordCorrect(String email, String password);
}
