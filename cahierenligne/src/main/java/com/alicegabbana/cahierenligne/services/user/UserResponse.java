package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import net.minidev.json.JSONObject;

@Stateless
public class UserResponse {
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	UserServiceLocal userService;
	
	Logger logger = Logger.getLogger(UserResponse.class);
	
	public Response login(JSONObject body) {
		
		String email = body.getAsString("username");
		String password = body.getAsString("password");
		
		if ( email == "" || 
				password == "" ) {
			logger.error("email or pass empty");
			return authService.returnResponse(400);
		}
		if ( userService.emailFormatCorrect(email) == false ) {
				System.out.println("email wrong format");
				return authService.returnResponse(400);
			}
				
		User user = userService.getByEmail(email);
		
		if ( user == null ) {
			logger.error("userExist false");
			return authService.returnResponse(401);
		}
		
		boolean validPassword = userService.isPasswordCorrect(email, password);
		
		if ( validPassword == false ) {
			logger.error("validPassword false");
			return authService.returnResponse(401);
		}
		
		UserDto userDto = userService.daoToDto(user);
		
		try {
			String token = authService.createAndReturnToken(email);
			userDto.setToken(token);
			return authService.returnResponseWithEntity(200, userDto);
		} catch (KeyLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authService.returnResponse(400); 
	}
	
}
