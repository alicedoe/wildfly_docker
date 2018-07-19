package com.alicegabbana.cahierenligne.services.user;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.setting.SettingServiceLocal;
import com.alicegabbana.cahierenligne.services.utils.PasswordUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Stateless
public class Userservice implements UserServiceLocal, UserServiceRemote {

	private static final long serialVersionUID = 8563372372793166338L;
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	SettingServiceLocal settingService;

	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(Userservice.class);
	
	public User create(User user) {
		String token = returnTokenUserByEmail(user.getEmail());
		String hashPwd = hashPassword(user.getPwd());
		user.setPwd(hashPwd);
		user.setToken(token);
		if (userIsComplete(user)) {
			User usercreated = em.merge(user);
			return usercreated;
		}
		logger.fatal("User is not complete : "+user.toString());
		throw new NullPointerException();
	}
	
	private Boolean userIsComplete(User user) {
		if ( user.getRole() == null 
				|| user.getName() == null 
				|| user.getFirstname() == null 
				|| user.getEmail() == null
				|| user.getToken() == null 
				|| user.getPwd() == null ) {
			return false;
		}
		return true;
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
		logger.info("Dao getByEmail : user with this email doesn't exist");				
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
	
	public UserDto daoToDto(User user) {
		
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
}
