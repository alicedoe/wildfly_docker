package com.alicegabbana.cahierenligne.services.user;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.dto.NewUserDto;
import com.alicegabbana.cahierenligne.dto.RoleDto;
import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.KidsClass;
import com.alicegabbana.cahierenligne.entities.Role;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.kidsclass.KidsclassException;
import com.alicegabbana.cahierenligne.services.kidsclass.KidsclassServiceLocal;
import com.alicegabbana.cahierenligne.services.role.RoleException;
import com.alicegabbana.cahierenligne.services.role.RoleServiceLocal;
import com.alicegabbana.cahierenligne.services.setting.SettingException;
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
	KidsclassServiceLocal kidsclassService;
	
	@EJB
	RoleServiceLocal roleService;

	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(Userservice.class);
	
	public UserDto login (JSONObject body) throws UserException, SettingException {
		String email = body.getAsString("username");
		String password = body.getAsString("password");
		
		if ( email == "" || 
				password == "" ) {
			throw new UserException (UserException.BAD_REQUEST,"Email or password empty");
		}
		if ( emailFormatCorrect(email) == false ) {
			throw new UserException (UserException.BAD_REQUEST,"Wrong email format");
			}
				
		User user = getByEmail(email);
		
		if ( user == null ) {
			throw new UserException (UserException.NOT_FOUND,"User does not exist");
		}
		
		try {
			boolean validPassword = isPasswordCorrect(email, password);
			
			if ( validPassword == false ) {
				throw new UserException (UserException.UNAUTHORIZED, "Wrong credits");
			}
			
			UserDto userDto = daoToDto(user);			

			return userDto;
//			try {
//				String token = authService.createAndReturnToken(email);
//				userDto.setToken(token);
//				return userDto;
//			} catch (KeyLengthException e) {
//				e.printStackTrace();
//				throw new UserException (UserException.INTERNAL_SERVER_ERROR, "Impossible to generate Token");
//			} catch (JOSEException e) {
//				e.printStackTrace();
//				throw new UserException (UserException.INTERNAL_SERVER_ERROR, "Impossible to generate Token");
//			}
		} catch (SettingException e) {
			throw new SettingException(e.getCode());
		}

	}
	
	public UserDto loginToken(JSONObject body) throws UserException {
		String token = body.getAsString("token");
				
		User user = getByToken(token);
		
		if ( user == null ) {
			throw new UserException (UserException.NOT_FOUND,"User does not exist");
		}
		
		UserDto userDto = daoToDto(user);
		return userDto;

	}
	
	public User create(User user) throws UserException, SettingException {
		String token = returnTokenUserByEmail(user.getEmail());
		try {
			String hashPwd = hashPassword(user.getPwd());
			user.setPwd(hashPwd);
			user.setToken(token);
			
			try {
				userIsComplete(user);
			} catch (UserException e) {
				throw new UserException (UserException.BAD_REQUEST, "User "+user.toString()+" incomplete !");
			}
			
			try {
				emailAvailable(user.getEmail());
				User usercreated = em.merge(user);
				return usercreated;
			} catch (UserException e) {
				throw new UserException(UserException.CONFLICT, e.getMessage());
			}
		} catch (SettingException e) {
			throw new SettingException(e.getCode());
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
			throw new UserException(UserException.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	public User getByToken (String token) throws UserException {
		
		TypedQuery<User> query_token = em.createQuery("SELECT user FROM User user WHERE user.token = :token", User.class)
				.setParameter("token", token);
		List<User> loadedUsers = query_token.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		throw new UserException(UserException.NOT_FOUND, "User with this token doesn't exist");
	}
	
	public boolean emailFormatCorrect(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
	
	public User getByEmail ( String email ) throws UserException {		
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", email);
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		} 
		
		return null;
	}
	
	public List<UserDto> getAll () {
		List<User> allUsers = em.createQuery("SELECT user FROM User user", User.class)
				.getResultList();
		
		List<UserDto> usersDto = userListToUserDtoList(allUsers);
		return usersDto;
	}
	
	public void deleteUser (Long id) throws UserException {
		try {
			User user = get(id);
			em.remove(user);
		} catch (UserException e) {
			throw new UserException(e.getCode(), e.getMessage());
		}
	}
	
	public User get(Long id) throws UserException {
		TypedQuery<User> query_id = em.createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
				.setParameter("id", id);
		List<User> loadedUsers = query_id.getResultList();

		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		throw new UserException(404, "No user with id "+id);
	}
	
	public RoleDto getRole(String token) throws UserException, RoleException {
		Role role;
		try {
			User user = getByToken(token);
			role = roleService.get(user.getRole().getName());
			return roleService.daoToDto(role);
		} catch (UserException e) {
			try {
				role = roleService.get("visitor");
				return roleService.daoToDto(role);
			} catch (RoleException e1) {
				throw new RoleException(e.getCode(), e.getMessage());
			}			
		} catch (RoleException e) {
			try {
				role = roleService.get("visitor");
				return roleService.daoToDto(role);
			} catch (RoleException e1) {
				throw new RoleException(e.getCode(), e.getMessage());
			}			
		}
	}

	public boolean isPasswordCorrect(String email, String password) throws SettingException {
		
		try {
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
		} catch (SettingException e) {
			throw new SettingException(e.getCode());
		}
		
	}
	
	private List<UserDto> userListToUserDtoList (List<User> userList) {

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = daoToDto(user);
			userDtoList.add(userDto);
		}

		return userDtoList;
	}
	
	private Boolean newUserIsCorrect(NewUserDto newUserDto) throws UserException {
		if ( newUserDto.getRoleName() == null 
				|| newUserDto.getName() == null 
				|| newUserDto.getFirstname() == null 
				|| newUserDto.getEmail() == null
				|| newUserDto.getPwd() == null ) {
			throw new UserException (UserException.BAD_REQUEST, "User "+newUserDto.toString()+" incomplete !");
		}
		
		try {
			roleService.get(newUserDto.getRoleName());
			try {
				emailAvailable(newUserDto.getEmail());
				return true;
			} catch (UserException e) {
				throw new UserException(UserException.CONFLICT, e.getMessage());
			}
		} catch (Exception e) {
			throw new UserException(UserException.NOT_FOUND, e.getMessage());
		}	
		
	}
	
	private Boolean userIsComplete(User user) throws UserException {
		if ( user.getRole() == null 
				|| user.getName() == null 
				|| user.getFirstname() == null 
				|| user.getEmail() == null
				|| user.getToken() == null 
				|| user.getPwd() == null ) {
			throw new UserException (UserException.BAD_REQUEST, "User "+user.toString()+" incomplete !");
		}
		return true;
	}
	
	private Boolean emailAvailable(String email) throws UserException {		
		if ( getByEmail(email) == null)
			return true;
		else throw new UserException(UserException.CONFLICT, "Email "+email+" is not available");
	}
	
	private String hashPassword(String password) throws SettingException {
		try {
			String salt = getSalt();
	        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
			return mySecurePassword;
		} catch (SettingException e) {
			throw new SettingException(e.getCode());
		}
	}
	
	private String getSalt() throws SettingException {	
		try {
			String salt = settingService.get("PASS_SALT").getParam();
			return salt;
		} catch (SettingException e) {
			throw new SettingException(SettingException.NOT_FOUND);
		}	
	}
	
	private String returnTokenUserByEmail (String email) throws SettingException, UserException {
		try {
			String token = authService.createAndReturnToken(email);
			return token;
		} catch (KeyLengthException e) {
			e.printStackTrace();
			throw new UserException(UserException.INTERNAL_SERVER_ERROR, "KeyLengthException");
		} catch (JOSEException e) {
			e.printStackTrace();
			throw new UserException(UserException.INTERNAL_SERVER_ERROR, "JOSEException");
		}
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
	
	private User dtoToDao(NewUserDto newUserDto) throws UserException, KidsclassException, RoleException {
		
		User user = new User();
		
		user.setEmail(newUserDto.getEmail());
		
		if (newUserDto.getKidsClassName() != null) {
			user.setKidsClass(getKidsClass(newUserDto));	
		}	
		
		user.setName(newUserDto.getName());
		user.setFirstname(newUserDto.getFirstname());
		user.setRole(getRole(newUserDto));
		
		return user;
	}
	
	private KidsClass getKidsClass (NewUserDto newUserDto) throws KidsclassException {
		try {
			KidsClass kidsClass = kidsclassService.getByName(newUserDto.getKidsClassName());
			return kidsClass;
		} catch (KidsclassException e) {
			throw new KidsclassException(e.getCode(), e.getMessage());
		}
	}
	
	private Role getRole (NewUserDto newUserDto) throws RoleException {
		try {
			Role role = roleService.get(newUserDto.getRoleName());
			return role;
		} catch (RoleException e) {
			throw new RoleException(e.getCode(), e.getMessage());
		}
	}
}
