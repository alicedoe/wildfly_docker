package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	public boolean validateEmail(String email) {		
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
	
	public boolean userIdExist (User user) {
		
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
	
	public Response createUser(User user) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( user.getEmail() == null ||  !validateEmail(user.getEmail()) ) 
		{	logger.info("invalid_null_email");
			return Response.status(400).build();
			}
		
		if ( user.getNom() == null || user.getPrenom() == null || user.getPwd() == null ) 
		{	logger.info("missing_attributes");
			return Response.status(400).build(); }
		
		if ( user.getId() != null || user.getEmail() == null )
		{	logger.info("forced_id");
			return Response.status(400).build(); }
		
		if ( !roleService.roleExist(user.getRole()) )
		{	logger.info("role_doesnt_exist");
			return Response.status(400).build(); }
		
		if ( userEmailExist(user) ) builder.status(Response.Status.CONFLICT);
		else {
			
			try {
				String token = authService.createToken(user.getEmail());
				user.setToken(token);
			} catch (KeyLengthException e) {
				e.printStackTrace();
				builder.status(Response.Status.LENGTH_REQUIRED);
				return builder.build();
			} catch (JOSEException e) {
				e.printStackTrace();
				return builder.build();
			}
			User newUser = em.merge(user);
			builder.status(Response.Status.OK);
			builder.entity(newUser);
		}
		
		return builder.build();
	}
	
	public Response getUser(User user) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		if ( user == null || user.getId() == null ) return Response.status(400).entity("missing_attributes").build();
		
		if ( !userIdExist(user) ) builder.status(Response.Status.NOT_FOUND);
		else {
			user = em.find(User.class, user.getId());
			builder.entity(user);
			builder.status(Response.Status.OK);
		}
		
		return builder.build();
	}
	
	public Response getAllUser() {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		return builder.build();
	}
	
	public Response deleteUser(User user) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		return builder.build();
	}
	
	public Response updateUser(User user) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		return builder.build();
	}
	
	
}
