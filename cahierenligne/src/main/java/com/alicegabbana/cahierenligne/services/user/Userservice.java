package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.KidsClass;
import com.alicegabbana.cahierenligne.entities.Role;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.kidsclass.KidsclassServiceLocal;
import com.alicegabbana.cahierenligne.services.role.RoleServiceLocal;
import com.alicegabbana.cahierenligne.services.setting.SettingServiceLocal;
import com.alicegabbana.cahierenligne.services.utils.PasswordUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import net.minidev.json.JSONObject;

@Stateless
public class Userservice implements UserServiceLocal, UserServiceRemote {

	private static final long serialVersionUID = 8563372372793166338L;
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	SettingServiceLocal settingService;
	
	@EJB
	KidsclassServiceLocal kidsclassServiceLocal;
	
	@EJB
	RoleServiceLocal roleServiceLocal;

	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(Userservice.class);
	
	public UserDto login (JSONObject body) throws UserException {
		String email = body.getAsString("username");
		String password = body.getAsString("password");
		
		if ( email == "" || 
				password == "" ) {
			throw new UserException ("Email or password empty");
		}
		if ( emailFormatCorrect(email) == false ) {
			throw new UserException ("Wrong email format");
			}
				
		User user = getByEmail(email);
		
		if ( user == null ) {
			throw new UserException ("User does not exist");
		}
		
		boolean validPassword = isPasswordCorrect(email, password);
		
		if ( validPassword == false ) {
			throw new UserException ("Wrong credits");
		}
		
		UserDto userDto = daoToDto(user);
		
		try {
			String token = authService.createAndReturnToken(email);
			userDto.setToken(token);
			return userDto;
		} catch (KeyLengthException e) {
			e.printStackTrace();
			throw new UserException ("Impossible to generate Token");
		} catch (JOSEException e) {
			e.printStackTrace();
			throw new UserException ("Impossible to generate Token");
		}
	}
	
	public User create(User user) throws UserException {
		String token = returnTokenUserByEmail(user.getEmail());
		String hashPwd = hashPassword(user.getPwd());
		user.setPwd(hashPwd);
		user.setToken(token);
		
		try {
			userIsComplete(user);
		} catch (UserException e) {
			throw new UserException ("User "+user.toString()+" incomplete !");
		}
		
		try {
			emailAvailable(user.getEmail());
			User usercreated = em.merge(user);
			return usercreated;
		} catch (UserException e) {
			throw new UserException(e.getMessage());
		}
		
	}
	
	public UserDto create(NewUserDto newUserDto) throws UserException {
		
		try {
			newUserIsCorrect(newUserDto);
			String token = returnTokenUserByEmail(newUserDto.getEmail());
			String hashPwd = hashPassword(newUserDto.getPwd());
			User user = dtoToDao(newUserDto);
			user.setPwd(hashPwd);
			user.setToken(token);
			User usercreated = em.merge(user);
			UserDto userDto = daoToDto(usercreated);
			return userDto;
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
		
	}
	
	private Boolean newUserIsCorrect(NewUserDto newUserDto) throws UserException {
		if ( newUserDto.getRoleName() == null 
				|| newUserDto.getName() == null 
				|| newUserDto.getFirstname() == null 
				|| newUserDto.getEmail() == null
				|| newUserDto.getPwd() == null ) {
			throw new UserException ("User "+newUserDto.toString()+" incomplete !");
		}
		
		try {
			roleServiceLocal.get(newUserDto.getRoleName());
			try {
				emailAvailable(newUserDto.getEmail());
				return true;
			} catch (UserException e) {
				throw new UserException(e.getMessage());
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}	
		
	}
	
	private Boolean userIsComplete(User user) throws UserException {
		if ( user.getRole() == null 
				|| user.getName() == null 
				|| user.getFirstname() == null 
				|| user.getEmail() == null
				|| user.getToken() == null 
				|| user.getPwd() == null ) {
			throw new UserException ("User "+user.toString()+" incomplete !");
		}
		return true;
	}
	
	private Boolean emailAvailable(String email) throws UserException {		
		if ( getByEmail(email) == null)
			return true;
		else throw new UserException("Email "+email+" is not available");
	}
	
	private String hashPassword(String password) {
        String salt = getSalt();
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
		return mySecurePassword;
	}
	
	private String getSalt() {		
		String salt = settingService.get("PASS_SALT").getParam();
		if ( salt == null) return null;
		return salt;		
	}
	
	private String returnTokenUserByEmail (String email) {
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
	
	public User getByToken (String token) {
		
		TypedQuery<User> query_token = em.createQuery("SELECT user FROM User user WHERE user.token = :token", User.class)
				.setParameter("token", token);
		List<User> loadedUsers = query_token.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		logger.info("Dao getByToken : user with this token doesn't exist");		
		return null;
	}
	
	public boolean emailFormatCorrect(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
	
	public User getByEmail ( String email ) {		
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", email);
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		} 
		
		return null;
	}

	public boolean isPasswordCorrect(String email, String password) {
		
		String hashPassword = hashPassword(password);
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", email);
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			if (loadedUsers.get(0).getPwd().equals(hashPassword)) {
				return true;
			} else return false;
		}
		return false;
	}
	
	private UserDto daoToDto(User user) {
		
		UserDto userDto = new UserDto();
		if (user != null) {
			if (user.getId() != null) userDto.setId(user.getId());
			if (user.getEmail() != null) userDto.setEmail(user.getEmail());
			if (user.getKidsClass() != null) userDto.setKidsClassName(user.getKidsClass().getName());
			if (user.getName() != null) userDto.setName(user.getName());
			if (user.getFirstname() != null) userDto.setFirstname(user.getFirstname());
			if (user.getRole() != null) userDto.setRoleName(user.getRole().getName());
			if (user.getToken() != null) userDto.setToken(user.getToken());
		}

		return userDto;
	}
	
	private User dtoToDao(NewUserDto newUserDto) throws UserException {
		
		User user = new User();
		
		user.setEmail(newUserDto.getEmail());
		
		if (newUserDto.getKidsClassName() != null) {
			KidsClass kidsClass = kidsclassServiceLocal.getByName(newUserDto.getKidsClassName());
			user.setKidsClass(kidsClass);
		}
			
		user.setName(newUserDto.getName());
		user.setFirstname(newUserDto.getFirstname());
		
		try {
			Role role = roleServiceLocal.get(newUserDto.getRoleName());
			user.setRole(role);
			return user;
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}
	
}
