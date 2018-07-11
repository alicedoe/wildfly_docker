package com.alicegabbana.restserver.services.user;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.UserDao;
import com.alicegabbana.restserver.dto.ActionDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.entity.User;
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.action.ActionService;
import com.alicegabbana.restserver.services.kidsclass.KidsClassService;
import com.alicegabbana.restserver.services.role.RoleService;
import com.alicegabbana.restserver.services.school.SchoolService;
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
	
	public User create(UserDto userDto) {
		User user = dtoToDao(userDto);
		String token = returnTokenUserByEmail(userDto.getEmail());
		if (token == null) return null;		
		user.setToken(token);
		System.out.println(user.toString());
		User usercreated = em.merge(user);
		return usercreated;
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
	
	public UserDto updateMyAccountService (UserDto myNewProfil) {
		User myCurrentProfil = em.find(User.class, myNewProfil.getId());
		User oldUserUpdated = updateMyAccount(myCurrentProfil, myNewProfil);
		em.merge(oldUserUpdated);
		UserDto userDto = daoToDto(oldUserUpdated);
		return userDto;
	}
	
	public UserDto updateService (UserDto newUserDtoProfil) {
		User oldUser = em.find(User.class, newUserDtoProfil.getId());
		User newUserProfil = updateUserProfil(oldUser, newUserDtoProfil);
		em.merge(newUserProfil);
		UserDto userDto = daoToDto(newUserProfil);
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
	
	public User dtoToDao(UserDto userDto) {
		
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
			
			if (userDto.getToken() != null) {
				user.setToken(userDto.getToken());
			}
		}
		
		return user;
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
		}

		return userDto;
	}
	
	public List<UserDto> userListToUserDtoList (List<User> userList) {

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			UserDto userDto = daoToDto(user);
			userDtoList.add(userDto);
		}

		return userDtoList;
	}
	
	public User updateMyAccount(User user, UserDto newUserProfil) {
		
		String firstname = newUserProfil.getFirstname();
		String name = newUserProfil.getName();
		String email = newUserProfil.getEmail();
		String token = newUserProfil.getToken();
		
		if (firstname != null) user.setFirstname(firstname);
		if (name != null) user.setName(name);
		if (email != null && emailFormatCorrect(email)) user.setEmail(email);
		if (token != null) user.setToken(token);
		
		return user;
	}
	
	public User updateUserProfil(User user, UserDto newUserProfil) {
		
		Role role = roleService.getDaoByName(newUserProfil.getRoleName());
		String firstname = newUserProfil.getFirstname();
		String name = newUserProfil.getName();
		String email = newUserProfil.getEmail();
		KidsClass kidsClass = kidsClassService.getByName(newUserProfil.getKidsClassName());
		String token = newUserProfil.getToken();
		
		if (role != null) user.setRole(role);
		if (firstname != null) user.setFirstname(firstname);
		if (name != null) user.setName(name);
		if (email != null && emailFormatCorrect(email)) user.setEmail(email);
		if (kidsClass != null) user.setKidsClass(kidsClass);
		if (token != null) user.setToken(token);
		
		return user;
	}
	
	public boolean emailFormatCorrect(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
	
	public boolean isUserEmailExist (String email) {
		
		if ( userDao.getByEmail(email) == null ) {
			return false;
		}
		
		return true;
	}
	
	public boolean itsMyAccount (String token, Long id) {		
		
		User currentUser = userDao.getByToken(token);

		if ( currentUser != null && id == currentUser.getId() ) return true;
		else return false;
	}
	
	public boolean isUserExist(UserDto userDto) {
		
		if ( userDto.getId() == null) return false;
		
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
		
		else if ( userDto.getName() == null || userDto.getFirstname() == null ) 
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
	
	public User getDaoById(Long id) {
		return userDao.get(id);
	}
	
	public UserDto getDtoById(Long id) {
		User user = userDao.get(id);
		if (user != null) return daoToDto(user);		
		return null;
	}
	
	public UserDto getDtoByEmail(String email) {
		User user = userDao.getByEmail(email);
		if (user != null) return daoToDto(user);		
		return null;
	}

	public boolean isPasswordCorrect(String email, String password) {
		return userDao.isPasswordCorrect(email, password);
	}
	
}
