package com.alicegabbana.restserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.UserDao;
import com.alicegabbana.restserver.dto.ActionDto;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Stateless
public class UserService {
	
	@EJB
	AuthService authService;
	
	@EJB
	RoleService roleService;
	
	@EJB
	UserDao userDao;
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	ActionService actionService;
	
	@EJB
	SchoolService schoolService;
	
	Logger logger = Logger.getLogger(UserService.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern;
	private Matcher matcher;
	
	public Response createResponse ( UserDto userDto) {
		
		if (userDto.getId() != null || newUserIsComplete(userDto) == false) return authService.returnResponse(400);

		if ( userEmailExist(userDto.getEmail()) ) return authService.returnResponse(409);
		
		String newToken = returnTokenUserByEmail(userDto.getEmail());
		
		if ( newToken == null ) return authService.returnResponse(400);
		
		User user = userDtoToUser(userDto);
		user.setToken(newToken);
		User newUser = em.merge(user);
		UserDto newUserDto = userToUserDto(newUser);
		return authService.returnResponseWithEntity(201, newUserDto);
	}
	
	public Response getResponse ( UserDto userDtoToFind ) {
		
		if ( userDtoToFind.getId() == null ) return authService.returnResponse(400);
		
		if ( getUserById(userDtoToFind.getId()) == null ) return authService.returnResponse(404);

		User user = em.find(User.class, getUserById(userDtoToFind.getId()));
		UserDto userDto = userToUserDto(user);
		return authService.returnResponseWithEntity(200, userDto);
	}
	
	public Response getWithRoleResponse ( RoleDto roleDto ) {
		
		if ( roleDto.getId() == null ) return authService.returnResponse(400);
		
		if ( roleService.getDaoByIdService(roleDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<UserDto> listUserDto = getWithRoleService(roleDto.getId());
		
		return authService.returnResponseWithEntity(200, listUserDto);
	}
	
	public Response getFromKidsClassResponse ( KidsClassDto kidsClassDto ) {
		
		if ( kidsClassDto.getId() == null ) return authService.returnResponse(400);
		
		if ( kidsClassService.getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);

		List<UserDto> listUserDto = getFromKidsClassService(kidsClassDto.getId());
		
		return authService.returnResponseWithEntity(200, listUserDto);
	}
	
	public Response getActionListResponse ( UserDto userDto ) {
		
		if ( userDto.getId() == null ) return authService.returnResponse(400);
		
		if ( getUserById(userDto.getId()) == null ) return authService.returnResponse(404);

		List<ActionDto> listActionDto = getActionService(userDto.getId());
		
		return authService.returnResponseWithEntity(200, listActionDto);
	}	
	
	public Response getAllResponse( ) {		
		return authService.returnResponseWithEntity(200, getAllService ());
	}
	
	public List<UserDto> getWithRoleService (Long id) {
		List<User> userList = userDao.getUserWithRole(id);
		if (userList != null) {
			List<UserDto> listUserDto = userListToUserDtoList(userList);
			return listUserDto;
		}
		
		return null;		
	}
	
	public List<UserDto> getAllService () {
		List<User> users = userDao.fetchAllUserDao();
		if ( users != null) {
			List<UserDto> userDtoList = userListToUserDtoList(users);
			return userDtoList;
		}		
		return null;		
	}
	
	
	public Response deleteResponse( UserDto userDto ) {
		
		if ( userDto == null || userDto.getId() == null ) return authService.returnResponse(400);
		
		if ( !userIdExistDao(userDto) ) return authService.returnResponse(404);

		deleteService(userDto.getId());
		return authService.returnResponse(200);
	}
	
	public Response updateResponse( UserDto newUserDtoProfil ) {
		
		if ( newUserDtoProfil == null || newUserDtoProfil.getId() == null ) return authService.returnResponse(400);
		
		if ( !userIdExistDao(newUserDtoProfil) ) return authService.returnResponse(404);
		
		UserDto userDto = updateService(newUserDtoProfil);
		return authService.returnResponseWithEntity(200, userDto);
	}
	
	public Response updateMyAccountResponse( UserDto myNewProfil ) {
		
		if ( myNewProfil == null || myNewProfil.getId() == null ) return authService.returnResponse(400);
		
		UserDto userDto = updateMyAccountService(myNewProfil);
		return authService.returnResponseWithEntity(200, userDto);
	}
	
//	Services & utilities
	
	public UserDto updateMyAccountService (UserDto myNewProfil) {
		User myCurrentProfil = em.find(User.class, myNewProfil.getId());
		User oldUserUpdated = updateMyAccount(myCurrentProfil, myNewProfil);
		em.merge(oldUserUpdated);
		UserDto userDto = userToUserDto(oldUserUpdated);
		return userDto;
	}
	
	public UserDto updateService (UserDto newUserDtoProfil) {
		User oldUser = em.find(User.class, newUserDtoProfil.getId());
		User newUserProfil = updateUserProfil(oldUser, newUserDtoProfil);
		em.merge(newUserProfil);
		UserDto userDto = userToUserDto(newUserProfil);
		return userDto;
	}
	
	public void deleteService (Long id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}
	
	public List<ActionDto> getActionService (Long id) {
		List<Action> actionList = userDao.getActionFromUser(id);				
		if (actionList != null) {
			List<ActionDto> listActionDto = actionService.daoListToDtoList(actionList);
			return listActionDto;
		}
		
		return null;		
	}
	
	public List<UserDto> getFromKidsClassService (Long id) {
		List<User> userList = userDao.getUserFromKidsClass(id);		
		if (userList != null) {
			List<UserDto> listUserDto = userListToUserDtoList(userList);
			return listUserDto;
		}
		
		return null;		
	}
	
	public User userDtoToUser (UserDto userDto) {
		
		User user = new User();
		if (userDto != null) {
			user.setId(userDto.getId());
			user.setEmail(userDto.getEmail());
			
			if (userDto.getKidsClassName() != null) {
				KidsClass kidsClass = kidsClassService.getByName(userDto.getKidsClassName());
				user.setKidsClass(kidsClass);
			}
			
			user.setName(userDto.getName());
			user.setFirstname(userDto.getFirstname());
			
			if (userDto.getRoleName() != null) {
				Role role = roleService.getDaoByName(userDto.getRoleName());
				user.setRole(role);
			}
			
			if (userDto.getPwd() != null) {
				user.setPwd(userDto.getPwd());
			}
		}
		
		return user;
	}
	
	public UserDto userToUserDto (User user) {
		
		UserDto userDto = new UserDto();
		if (user != null) {
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			
			if (user.getKidsClass() != null) {
				userDto.setKidsClassName(user.getKidsClass().getName());
			}
			
			userDto.setName(user.getName());
			userDto.setFirstname(user.getFirstname());
			
			if (user.getRole() != null) {
				userDto.setRoleName(user.getRole().getName());
			}
		}
		
		return userDto;
	}
	
	public List<UserDto> userListToUserDtoList (List<User> userList) {

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = userToUserDto(user);
			userDtoList.add(userDto);
		}

		return userDtoList;
	}
	
	public User updateMyAccount(User user, UserDto newUserProfil) {
		
		String firstname = newUserProfil.getFirstname();
		String name = newUserProfil.getName();
		String email = newUserProfil.getEmail();
		String pwd = newUserProfil.getPwd();
		
		if (firstname != null) user.setFirstname(firstname);
		if (name != null) user.setName(name);
		if (email != null && emailFormatCorrect(email)) user.setEmail(email);
		if (pwd != null) user.setPwd(pwd);
		
		return user;
	}
	
	public User updateUserProfil(User user, UserDto newUserProfil) {
		
		Role role = roleService.getDaoByName(newUserProfil.getRoleName());
		String firstname = newUserProfil.getFirstname();
		String name = newUserProfil.getName();
		String email = newUserProfil.getEmail();
		KidsClass kidsClass = kidsClassService.getByName(newUserProfil.getKidsClassName());
		String pwd = newUserProfil.getPwd();
		
		if (role != null) user.setRole(role);
		if (firstname != null) user.setFirstname(firstname);
		if (name != null) user.setName(name);
		if (email != null && emailFormatCorrect(email)) user.setEmail(email);
		if (kidsClass != null) user.setKidsClass(kidsClass);
		if (pwd != null) user.setPwd(pwd);
		
		return user;
	}
	
	public boolean emailFormatCorrect(String email) {		
		logger.info("validateEmail : " + email);	
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		logger.info("Email is valid : " + matcher.matches());
		return matcher.matches();
	}
	
	public boolean userEmailExist (String email) {
		
		if ( userDao.getByEmail(email) == null ) {
			return false;
		}
		
		return false;
	}
	
	public boolean itsMyAccount (String token, Long id) {		
		
		User currentUser = userDao.getByToken(token);

		if ( currentUser != null && id == currentUser.getId() ) return true;
		else return false;
	}
	
	public boolean userIdExistDao (UserDto userDto) {
		
		User currentUser = userDao.get(userDto.getId());
		
		if ( currentUser != null ) return true;
		else return false;
	}
	
	public String returnTokenUserByEmail (String email) {
		try {
			String token = authService.createAndReturnToken(email);
			return token;
		} catch (KeyLengthException e) {
			e.printStackTrace();
			return null;
		} catch (JOSEException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean newUserIsComplete (UserDto userDto) {
		if ( userDto.getEmail() == null ||  !emailFormatCorrect(userDto.getEmail()) ) 
		{	logger.info("invalid_null_email");
			return false;
			}
		
		else if ( userDto.getName() == null || userDto.getFirstname() == null || userDto.getPwd() == null ) 
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( userDto.getId() != null || userDto.getEmail() == null )
		{	logger.info("forced_id");
			return false; }
		
		else if ( roleService.getDaoByName( userDto.getRoleName() ) == null )
		{	logger.info("role_doesnt_exist");
			return false; }
		
		return true;
	}
	
	public User getUserById (Long id) {
		if ( userDao.get(id) != null ) {
			return userDao.get(id);
		}
		
		return null;
	}
	
}
