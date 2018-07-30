package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.setting.SettingException;

import net.minidev.json.JSONObject;

@Local
public interface UserServiceLocal {
	UserDto login (JSONObject body) throws UserException, SettingException;
	UserDto create(NewUserDto newUserDto) throws UserException;
	User getByToken (String token) throws UserException;
	List<User> getAll();
	void deleteUser (Long id) throws UserException;
	User get(Long id) throws UserException;
}
