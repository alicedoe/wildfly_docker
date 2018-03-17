package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class UserService {
	
	@EJB
	AuthService authService;
	
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

		if ( authService.userIsAuthorized(userToken, actionsNeeded) == false && itsMyAccount(userToken, user) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		if ( user == null || user.getId() == null ) builder.status(Response.Status.BAD_REQUEST);
		
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
		
		List<User> allUsers = fetchAllUserDao();	
		logger.error("nombre d'users : "+allUsers.size());
		builder.entity(allUsers);
		builder.status(Response.Status.OK);
		return builder.build();
	}
	
	public Response deleteUserService( String userToken, List<String> actionsNeeded, User user ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		return builder.build();
	}
	
	public Response updateUser( String userToken, List<String> actionsNeeded, User user ) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		if (authService.userIsAuthorized(userToken, actionsNeeded) == false ) 
			return builder.status(Response.Status.UNAUTHORIZED).build();
		
		return builder.build();
	}
	
	public boolean emailFormatCorrect(String email) {		
		logger.info("validateEmail : " + email);	
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		logger.info("Email is valid : " + matcher.matches());
		return matcher.matches();
	}
	
	public boolean userEmailExist (User user) {
		
		TypedQuery<User> query_email = em.createQuery("SELECT user FROM User user WHERE user.email = :email", User.class)
				.setParameter("email", user.getEmail());
		List<User> loadedUsers = query_email.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			logger.info("Dao createUser : email already exist");	
			return true;
		}
		logger.info("Dao createUser : user doesn't exist");		
		return false;
	}
	
	public boolean itsMyAccount (String token, User user) {		
		if ( user.getId() == getUserByToken(token).getId() )
		return true;
		else return false;
	}
	
	public User getUserByToken (String token) {
		
		TypedQuery<User> query_token = em.createQuery("SELECT user FROM User user WHERE user.token = :token", User.class)
				.setParameter("token", token);
		List<User> loadedUsers = query_token.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			logger.info("Dao createUser : email already exist");	
			return loadedUsers.get(0);
		}
		logger.info("Dao createUser : user doesn't exist");		
		return null;
	}
	
	public List<User> fetchAllUserDao () {
		List<User> userDetailsList = em.createQuery("SELECT user FROM User user", User.class)
				.getResultList();
		return userDetailsList;
	}
	
	public boolean userIdExistDao (User user) {
		
		TypedQuery<User> query_id = em.createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
				.setParameter("id", user.getId());
		List<User> loadedUsers = query_id.getResultList();
		
		if ( loadedUsers.size() != 0 ) {
			logger.info("Dao createUser : id exist");	
			return true;
		}
		logger.info("Dao createUser : id doesn't exist");		
		return false;
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
		
		else if ( !roleService.roleNameExist(user.getRole()) )
		{	logger.info("role_doesnt_exist");
			return false; }
		
		return true;
	}
	
}
