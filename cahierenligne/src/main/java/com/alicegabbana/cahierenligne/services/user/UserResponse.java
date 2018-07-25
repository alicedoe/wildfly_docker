package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;

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
			} catch (UserException e) {
				e.printStackTrace();
				return authService.returnResponse(400);
			}
			
		return authService.returnResponseWithEntity(200, userDto);
	}
	
	public Response create(NewUserDto newUserDto) {
		
		try {
			UserDto userDto = userService.create(newUserDto);
			return authService.returnResponseWithEntity(200, userDto);
		} catch (UserException e) {
			e.printStackTrace();
			return authService.returnResponse(400);
		}			
		
	}
	
}
