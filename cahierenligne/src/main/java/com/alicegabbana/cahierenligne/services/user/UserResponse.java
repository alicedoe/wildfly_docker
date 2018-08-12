package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.RoleDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.role.RoleException;
import com.alicegabbana.cahierenligne.services.setting.SettingException;

import net.minidev.json.JSONObject;

@Stateless
public class UserResponse {
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	UserServiceLocal userService;
	
	public Response login(JSONObject body) {
		UserDto userDto;
		
		try {
			userDto = userService.login(body);
			return authService.returnResponse(200, userDto);
		} catch (UserException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		} catch (SettingException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(400, jsonObject);
		}
	}
	
	public Response create(NewUserDto newUserDto) {
		
		try {
			UserDto userDto = userService.create(newUserDto);
			return authService.returnResponse(200, userDto);
		} catch (UserException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}			
		
	}
	
	public Response getAll() {
		List<UserDto> allUser =  userService.getAll();
		return authService.returnResponse(200, allUser);
	}
	
	public Response delete( Long id ) {
		try {
			userService.deleteUser(id);
			return authService.returnResponse(200);
		} catch (UserException e) {
			return authService.returnResponse(404);
		}		
	}
	
	public Response get( Long id ) {
		try {
			User user = userService.get(id);
			return authService.returnResponse(200, user);
		} catch (UserException e) {
			return authService.returnResponse(404);
		}
	}
	
	public Response getRole( String token ) {
		try {
			RoleDto roleDto = userService.getRole(token);
			return authService.returnResponse(200, roleDto);
		} catch (UserException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		} catch (RoleException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}
	}
	
}
