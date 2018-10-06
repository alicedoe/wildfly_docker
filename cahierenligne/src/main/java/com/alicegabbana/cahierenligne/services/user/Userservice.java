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

import com.alicegabbana.cahierenligne.dto.UserDto;
import com.alicegabbana.cahierenligne.entities.Role;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;
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
	
	public UserDto create(UserDto userDto) throws UserException, RoleException, SettingException {
		
		if ( userDto.getId() != null ) {
			throw new UserException(UserException.BAD_REQUEST, "New user can not have an id");
		}
		
		try {
			userFieldsAreNotEmpty(userDto, false);
			userEmailIsCorrect(userDto);
			userRoleIsCorrect(userDto);
			userEmailIsAvailable(userDto);			
			String token = returnTokenUserByEmail(userDto.getEmail());
			String hashPwd = hashPassword(userDto.getPwd());
			User user = dtoToDao(userDto);
			user.setPwd(hashPwd);
			user.setToken(token);
			User usercreated = em.merge(user);
			UserDto userCreatedDto = daoToDto(usercreated);
			return userCreatedDto;
		} catch (UserException e) {
			throw new UserException(e.getCode(), e.getMessage());
		} catch (RoleException e) {
			throw new RoleException(e.getCode(), e.getMessage());
		} catch (SettingException e) {
			throw new SettingException(e.getCode(), e.getMessage());
		}
		
	}
	
	public UserDto update(UserDto userDto) throws UserException, RoleException, SettingException {		
		
		if ( userDto.getId() == null ) {
			throw new UserException(UserException.BAD_REQUEST, "User has no id");
		}

		User oldUser = getUser(userDto.getId());
		String token = oldUser.getToken();
		
		try {
			userFieldsAreNotEmpty(userDto, true);
			userEmailIsCorrect(userDto);
			userRoleIsCorrect(userDto);
			if ( !oldUser.getEmail().equals(userDto.getEmail())) {
				userEmailIsAvailable(userDto);
				token = returnTokenUserByEmail(userDto.getEmail());
			}
			User user = dtoToDao(userDto);
			if ( userDto.getPwd() != null && !userDto.getPwd().equals("")  ) {
				String hashPwd = hashPassword(userDto.getPwd());
				user.setPwd(hashPwd);
			} else {
				user.setPwd(oldUser.getPwd());
			}
			user.setToken(token);
			User userCreated = em.merge(user);
			UserDto userCreatedDto = daoToDto(userCreated);
			return userCreatedDto;
		} catch (UserException e) {
			throw new UserException(e.getCode(), e.getMessage());
		} catch (RoleException e) {
			throw new RoleException(e.getCode(), e.getMessage());
		} catch (SettingException e) {
			throw new SettingException(e.getCode(), e.getMessage());
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
			User user = getUser(id);
			em.remove(user);
		} catch (UserException e) {
			throw new UserException(e.getCode(), e.getMessage());
		}
	}
	
	public User getUser(Long id) throws UserException {
		TypedQuery<User> query_id = em.createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
				.setParameter("id", id);
		List<User> loadedUsers = query_id.getResultList();

		if ( loadedUsers.size() != 0 ) {
			return loadedUsers.get(0);
		}
		throw new UserException(404, "No user with id "+id);
	}
	
	public UserDto getUserDto(Long id) throws UserException {		
		try {
			User user = getUser(id);
			UserDto userDto = daoToDto(user);
			return userDto;
		} catch ( UserException e) {
			throw new UserException(e.getCode(), e.getMessage());
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
	
	private Boolean userFieldsAreNotEmpty(UserDto userDto, boolean update) throws UserException {
		if ( 
				userDto.getRoleName() == null || userDto.getRoleName().isEmpty()
				|| userDto.getName() == null || userDto.getName().isEmpty()
				|| userDto.getFirstname() == null || userDto.getFirstname().isEmpty()
				|| userDto.getEmail() == null || userDto.getEmail().isEmpty()
			) {
			throw new UserException (UserException.BAD_REQUEST, "User "+userDto.toString()+" incomplete !");
		}
		
		if ( 
				!update && ( userDto.getPwd() == null || userDto.getPwd().isEmpty() )
			) {
			throw new UserException (UserException.BAD_REQUEST, "User "+userDto.toString()+" incomplete !");
		}
		
		return true;		
	}
	
	private Boolean userEmailIsCorrect(UserDto userDto) throws UserException {
		if ( !emailFormatCorrect(userDto.getEmail()) ) {
			throw new UserException(UserException.BAD_REQUEST, "Wrong email format");
		}		
		return true;		
	}
	
	private Boolean userRoleIsCorrect(UserDto userDto) throws UserException {
		try {
			roleService.get(userDto.getRoleName());
		} catch (Exception e) {
			throw new UserException(UserException.NOT_FOUND, e.getMessage());
		}		
		return true;		
	}
	
	private Boolean userEmailIsAvailable(UserDto userDto) throws UserException {
		try {
			emailAvailable(userDto.getEmail());
			return true;
		} catch (UserException e) {
			throw new UserException(UserException.CONFLICT, e.getMessage());
		}		
	}
	
	private Boolean userIsComplete(User user) throws UserException {
		if ( user.getRole() == null
				|| user.getName() == null || user.getName().isEmpty() 
				|| user.getFirstname() == null || user.getFirstname().isEmpty()
				|| user.getEmail() == null || user.getEmail().isEmpty()
				|| user.getToken() == null  || user.getToken().isEmpty()
				|| user.getPwd() == null || user.getPwd().isEmpty()
			) {
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
			if (user.getName() != null) userDto.setName(user.getName());
			if (user.getFirstname() != null) userDto.setFirstname(user.getFirstname());
			if (user.getRole() != null) userDto.setRoleName(user.getRole().getName());
			if (user.getToken() != null) userDto.setToken(user.getToken());
		}
		return userDto;
	}
	
	private User dtoToDao(UserDto userDto) throws UserException, RoleException {
		
		User user = new User();
		
		if (userDto.getId() != null) user.setId(userDto.getId());
		
		user.setEmail(userDto.getEmail());
		
		user.setName(userDto.getName());
		user.setFirstname(userDto.getFirstname());
		user.setRole(getRole(userDto));
		
		return user;
	}
	
	private Role getRole (UserDto userDto) throws RoleException {
		try {
			Role role = roleService.get(userDto.getRoleName());
			return role;
		} catch (RoleException e) {
			throw new RoleException(e.getCode(), e.getMessage());
		}
	}
}
