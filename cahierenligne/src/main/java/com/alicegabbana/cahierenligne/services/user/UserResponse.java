package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.RoleDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.role.RoleException;
import com.alicegabbana.cahierenligne.services.role.RoleServiceLocal;
import com.alicegabbana.cahierenligne.services.setting.SettingException;

import net.minidev.json.JSONObject;

@Stateless
public class UserResponse {
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	UserServiceLocal userService;
	
	@EJB
	RoleServiceLocal roleServiceLocal;
	
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
	
	public Response loginToken(JSONObject body) {
		UserDto userDto;
		
		try {
			userDto = userService.loginToken(body);
			return authService.returnResponse(200, userDto);
		} catch (UserException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}
	}
	
	public Response create(UserDto userDto) {
				
		try {
			UserDto userCreatedDto = userService.create(userDto);
			return authService.returnResponse(200, userCreatedDto);
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
		} catch (SettingException e) {
			JsonObject jsonObject = Json.createObjectBuilder()
					   .add("message", e.getMessage())
					   .build();
			return authService.returnResponse(e.getCode(), jsonObject);
		}			
		
	}
	
	public Response update(UserDto userDto) {
		
		try {
			UserDto userUpdatedDto = userService.update(userDto);
			System.out.println(userUpdatedDto);
			return authService.returnResponse(200, userUpdatedDto);
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
		} catch (SettingException e) {
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
			UserDto userDto = userService.getUserDto(id);
			return authService.returnResponse(200, userDto);
		} catch (UserException e) {
			return authService.returnResponse(404);
		}
	}
	
	public Response userIsAdmin( String token ) {
		try {
			User user = userService.getByToken(token);
			RoleDto roleDto = roleServiceLocal.daoToDto(user.getRole());
			boolean isAdmin;
			if ( roleDto.getName().equals("admin") ) isAdmin=true;
			else isAdmin = false;
			return authService.returnResponse(200, isAdmin);
		} catch (UserException e) {
			return authService.returnResponse(404);
		}
	}
	
}
