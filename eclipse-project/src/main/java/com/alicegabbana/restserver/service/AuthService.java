package com.alicegabbana.restserver.service;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dao.AdminDao;
import com.alicegabbana.restserver.dao.AuthDao;
import com.alicegabbana.restserver.dao.UserDao;
import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Stateless
public class AuthService {
	
	@EJB
	AdminDao adminDao;
	
	@EJB
	AuthDao authDao;
	
	@EJB
	UserDao userDao;
	
	@EJB
	ActionDao actionDao;
	
	boolean userIsGranted;
	Logger logger = Logger.getLogger(AuthService.class);

	public String createAndReturnToken(String email) throws KeyLengthException, JOSEException {
		
		int tokenDelay = Integer.parseInt(adminDao.getSettingByName("TOKEN_EXP").getParam());
		String api_key = adminDao.getSettingByName("API_KEY").getParam();
		LocalDate currentDate = new LocalDate();
		LocalDate expiration = currentDate.plus(Period.days(tokenDelay));
		

		JWSSigner signer;

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		    .subject(email)
		    .expirationTime(expiration.toDate())
		    .build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		signer = new MACSigner(api_key);
		signedJWT.sign(signer);
		
		return signedJWT.serialize();
		
	}
	
	public boolean userHasActionList (String token, List<String> actions) {		

		final String currentUserToken = token;
		
		actions.forEach(new Consumer<String>() {
			public void accept(String actionName) {
				Action action = actionDao.getActionByName(actionName);
				if ( !authDao.userHasThisAction(currentUserToken, action) ) {
					userIsGranted = false;
				} else userIsGranted = true;
			}
		});
		logger.info("userCanDoListOfActions - user is granted : "+userIsGranted);
		return userIsGranted;
	}
	
	public boolean itsMyAccount (String token, User user) {		
		
		User currentUser = userDao.getByToken(token);
		
		if ( currentUser != null && currentUser.getId() == user.getId() ) {
			return true;
			
		} else return false;
		
	}
	
	public Response returnResponse (int status) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		builder.status(status);
		
		return builder.build();
	}
	
	public Response returnResponseWithEntity (int status, Object entity) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		builder.status(status);
		if (entity != null) builder.entity(entity);
		
		return builder.build();
	}
	
}