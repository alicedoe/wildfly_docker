package com.alicegabbana.restserver.services.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.ActionDto;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.User;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.kidsclass.KidsClassService;
import com.alicegabbana.restserver.services.role.RoleService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import net.minidev.json.JSONObject;

@Stateless
public class UserResponse {
	
	@EJB
	AuthService authService;
	
	@EJB
	RoleService roleService;
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	UserService userService;
	
	Logger logger = Logger.getLogger(UserResponse.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Response create ( UserDto userDto) {
		
		if (userDto.getId() != null || userService.newUserIsComplete(userDto) == false) return authService.returnResponse(400);

		if ( userService.isUserEmailExist(userDto.getEmail()) ) return authService.returnResponse(409);
		
		User user = userService.create(userDto);		
		if ( user == null ) return authService.returnResponse(400);
		
		UserDto userDtoCreated = userService.daoToDto(user);
		
		return authService.returnResponseWithEntity(201, userDtoCreated);
	}
	
	public Response get ( UserDto userDto ) {
		
		if ( userDto.getId() == null ) return authService.returnResponse(400);
		
		UserDto userDtoFind = userService.getDtoById(userDto.getId());
		if ( userDtoFind == null ) return authService.returnResponse(404);

		return authService.returnResponseWithEntity(200, userDtoFind);
	}
	
	public Response getWithRole ( RoleDto roleDto ) {
		
		if ( roleDto.getId() == null ) return authService.returnResponse(400);
		
		if ( roleService.getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<UserDto> userDtoList = userService.getWithRoleService(roleDto.getId());
		
		return authService.returnResponseWithEntity(200, userDtoList);
	}
	
	public Response getFromKidsClass ( KidsClassDto kidsClassDto ) {
		
		if ( kidsClassDto.getId() == null ) return authService.returnResponse(400);
		
		if ( kidsClassService.getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);

		List<UserDto> listUserDto = userService.getFromKidsClassService(kidsClassDto.getId());
		
		return authService.returnResponseWithEntity(200, listUserDto);
	}
	
	public Response getActionListResponse ( UserDto userDto ) {
		
		if ( userDto.getId() == null ) return authService.returnResponse(400);
		
		if ( userService.getDaoById(userDto.getId()) == null ) return authService.returnResponse(404);

		List<ActionDto> listActionDto = userService.getActionService(userDto.getId());
		
		return authService.returnResponseWithEntity(200, listActionDto);
	}	
	
	public Response getAll( ) {		
		return authService.returnResponseWithEntity(200, userService.getAllService ());
	}
	
	
	public Response delete( UserDto userDto ) {
		
		if ( userDto == null || userDto.getId() == null ) return authService.returnResponse(400);
		
		if ( userService.getDaoById(userDto.getId()) == null ) return authService.returnResponse(404);

		userService.deleteService(userDto.getId());
		return authService.returnResponse(200);
	}
	
	public Response update( UserDto newUserDtoProfil ) {
		
		if ( newUserDtoProfil == null || newUserDtoProfil.getId() == null ) return authService.returnResponse(400);
		
		if ( userService.getDaoById(newUserDtoProfil.getId()) == null ) return authService.returnResponse(404);
		
		UserDto userDto = userService.updateService(newUserDtoProfil);
		return authService.returnResponseWithEntity(200, userDto);
	}
	
	public Response updateMyAccount( UserDto myNewProfil ) {
		
		if ( myNewProfil == null || myNewProfil.getId() == null ) return authService.returnResponse(400);
		
		UserDto userDto = userService.updateMyAccountService(myNewProfil);
		return authService.returnResponseWithEntity(200, userDto);
	}

	public Response login(JSONObject body) {
		
		String email = body.getAsString("username");
		String password = body.getAsString("password");
		
		if ( email == "" || 
				password == "" ) {
			System.out.println("email or pass empty");
			return authService.returnResponse(400);
		}
		if ( userService.emailFormatCorrect(email) == false ) {
				System.out.println("email wrong format");
				return authService.returnResponse(400);
			}
				
		UserDto userDto = userService.getDtoByEmail(email);
		
		boolean userExist = userService.isUserExist(userDto);
		boolean validPassword = userService.isPasswordCorrect(email, password);
		
		if ( userExist == false ) {
			System.out.println("userExist false");
			return authService.returnResponse(401);
		}
		
		if ( validPassword == false ) {
			System.out.println("validPassword false");
			return authService.returnResponse(401);
		}
		
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
