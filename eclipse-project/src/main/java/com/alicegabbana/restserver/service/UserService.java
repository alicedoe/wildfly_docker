package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.UserDao;
import com.alicegabbana.restserver.modelDao.Kidsclass;
import com.alicegabbana.restserver.modelDao.Role;
import com.alicegabbana.restserver.modelDao.User;
import com.alicegabbana.restserver.modelDto.UserDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class UserService {
	
	@EJB
	AuthService authService;
	
	@EJB
	UserDao userDao;
	
	@EJB
	RoleService roleService;
	
	Logger logger = Logger.getLogger(UserService.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern;
	private Matcher matcher;
	
	public Response createUserService( String userToken, List<String> actionsNeeded, User user) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		if ( newUserIsComplete(user) == false ) return builder.status(Response.Status.BAD_REQUEST).build();
		
		if ( userEmailExist(user) ) return builder.status(Response.Status.CONFLICT).build();
		
		String newToken = createTokenUser(user);
		
		if ( newToken == null ) return builder.status(Response.Status.BAD_REQUEST).build();

		user.setToken(newToken);		
		User newUser = em.merge(user);
		builder.status(Response.Status.OK);
		builder.entity(newUser);
		
		return builder.build();
	}
	
	public Response getUserService ( String userToken, List<String> actionsNeeded, User user ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( authService.userIsAuthorized(userToken, actionsNeeded) == false && itsMyAccount(userToken, user.getId()) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		if ( user == null || user.getId() == null ) return builder.status(Response.Status.BAD_REQUEST).build();
		
		if ( !userIdExistDao(user) ) return builder.status(Response.Status.NOT_FOUND).build();

		user = em.find(User.class, user.getId());
		builder.entity(user);
		builder.status(Response.Status.OK);
		return builder.build();
	}
	
	public Response getAllUserService( String userToken, List<String> actionsNeeded ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		List<User> allUsers = userDao.fetchAllUserDao();	
		logger.error("nombre d'users : "+allUsers.size());
		
		List<UserDto> userDtoList = userListToUserDtoList(allUsers);
		
		logger.error("nombre d'users dto : "+userDtoList.size());
		builder.entity(userDtoList);
		builder.status(Response.Status.OK);
		return builder.build();
	}
	
	public Response deleteUserService( String userToken, List<String> actionsNeeded, User user ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		if ( user == null || user.getId() == null ) return builder.status(Response.Status.BAD_REQUEST).build();
		
		if ( !userIdExistDao(user) ) return builder.status(Response.Status.NOT_FOUND).build();

		user = em.find(User.class, user.getId());
		em.remove(user);
		builder.status(Response.Status.OK);
		
		return builder.build();
	}
	
	public Response updateUser( String userToken, List<String> actionsNeeded, User newUserProfil ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		if ( newUserProfil == null || newUserProfil.getId() == null ) return builder.status(Response.Status.BAD_REQUEST).build();
		
		if ( !userIdExistDao(newUserProfil) ) return builder.status(Response.Status.NOT_FOUND).build();
		
		User oldUser = em.find(User.class, newUserProfil.getId());
		newUserProfil = updateUserProfil(oldUser, newUserProfil);
		em.merge(newUserProfil);
		builder.status(Response.Status.OK);
		
		return builder.build();
	}
	
	public Response askUpdateMyAcount( String userToken, List<String> actionsNeeded, UserDto myNewProfil ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if ( itsMyAccount(userToken, myNewProfil.getId()) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		User myCurrentProfil = em.find(User.class, myNewProfil.getId());
		User oldUserUpdated = updateMyAccount(myCurrentProfil, myNewProfil);
		em.merge(oldUserUpdated);
		builder.status(Response.Status.OK);
		
		return builder.build();
	}
	
//	Utilities
	
	public UserDto userToUserDto (User user) {
		
		UserDto userDto = new UserDto();
		if (user != null) {
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			
			if (user.getKidsClass() != null) {
				userDto.setKidsClassName(user.getKidsClass().getNom());
			}
			
			userDto.setNom(user.getNom());
			userDto.setPrenom(user.getPrenom());
			
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
		
		String prenom = newUserProfil.getPrenom();
		String nom = newUserProfil.getNom();
		String email = newUserProfil.getEmail();
		String pwd = newUserProfil.getPwd();
		
		if (prenom != null) user.setPrenom(prenom);
		if (nom != null) user.setNom(nom);
		if (email != null && emailFormatCorrect(email)) user.setEmail(email);
		if (pwd != null) user.setPwd(pwd);
		
		return user;
	}
	
	public User updateUserProfil(User user, User newUserProfil) {
		
		Role role = newUserProfil.getRole();
		String prenom = newUserProfil.getPrenom();
		String nom = newUserProfil.getNom();
		String email = newUserProfil.getEmail();
		Kidsclass kidsClass = newUserProfil.getKidsClass();
		String pwd = newUserProfil.getPwd();
		
		if (role != null) user.setRole(role);
		if (prenom != null) user.setPrenom(prenom);
		if (nom != null) user.setNom(nom);
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
	
	public boolean userEmailExist (User user) {
		
		if ( userDao.getByEmail(user.getEmail()) == null ) {
			return false;
		}
		
		return false;
	}
	
	public boolean itsMyAccount (String token, Long id) {		
		
		User currentUser = userDao.getByToken(token);

		if ( currentUser != null && id == currentUser.getId() ) return true;
		else return false;
	}
	
	public boolean userIdExistDao (User user) {
		
		User currentUser = userDao.get(user.getId());
		
		if ( currentUser != null ) return true;
		else return false;
	}
	
	public String createTokenUser (User user) {
		try {
			String token = authService.createToken(user.getEmail());
			return token;
		} catch (KeyLengthException e) {
			e.printStackTrace();
			return null;
		} catch (JOSEException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean newUserIsComplete (User user) {
		if ( user.getEmail() == null ||  !emailFormatCorrect(user.getEmail()) ) 
		{	logger.info("invalid_null_email");
			return false;
			}
		
		else if ( user.getNom() == null || user.getPrenom() == null || user.getPwd() == null ) 
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( user.getId() != null || user.getEmail() == null )
		{	logger.info("forced_id");
			return false; }
		
		else if ( !roleService.roleNameExist(user.getRole().getName()) )
		{	logger.info("role_doesnt_exist");
			return false; }
		
		return true;
	}
	
}
