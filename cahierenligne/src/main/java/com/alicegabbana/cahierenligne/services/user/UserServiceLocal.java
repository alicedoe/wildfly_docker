package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.role.RoleException;
import com.alicegabbana.cahierenligne.services.setting.SettingException;

import net.minidev.json.JSONObject;

@Local
public interface UserServiceLocal {
	UserDto login (JSONObject body) throws UserException, SettingException;
	UserDto loginToken(JSONObject body) throws UserException;
	UserDto create(UserDto userDto) throws UserException, RoleException, SettingException;
	UserDto update(UserDto userDto) throws UserException, RoleException, SettingException;
	User getByToken (String token) throws UserException;
	List<UserDto> getAll();
	void deleteUser (Long id) throws UserException;
	User getUser(Long id) throws UserException;
	UserDto getUserDto(Long id) throws UserException;
}
