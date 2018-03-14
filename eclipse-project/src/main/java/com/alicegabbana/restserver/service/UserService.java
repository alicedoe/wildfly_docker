package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.AuthDao;
import com.alicegabbana.restserver.dao.UserDao;
import com.alicegabbana.restserver.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class UserService {
	
	@EJB
	UserDao userDao;
	
	Logger logger = Logger.getLogger(UserService.class);
	
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern;
	private Matcher matcher;
	
	public boolean validateEmail(String email) {		
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public User createUser(User user) {
		
		if ( user.getId() != null ) {
			String message = "Id must be null for Role creation : " + user;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		if ( !validateEmail(user.getEmail()) ) {
			return null;
		}
		
		User newUser = userDao.createUser(user);
		
		return newUser;
	}
}
