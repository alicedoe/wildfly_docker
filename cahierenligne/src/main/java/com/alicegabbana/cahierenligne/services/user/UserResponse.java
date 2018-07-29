package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
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
	
}