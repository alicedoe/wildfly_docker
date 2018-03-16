package com.alicegabbana.restserver.service;

import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.alicegabbana.restserver.dao.AdminDao;
import com.alicegabbana.restserver.dao.AuthDao;
import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.User;
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
	
	boolean userIsGranted;
	Logger logger = Logger.getLogger(AuthService.class);

	public String createToken(String email) throws KeyLengthException, JOSEException {
		
		int tokenDelay = Integer.parseInt(adminDao.getParam("TOKEN_EXP").getParam());
		String api_key = adminDao.getParam("API_KEY").getParam();
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
	
	public boolean userCanDoListOfActions (String token, List<String> actions) {		

		final String currentUserToken = token;
		
		actions.forEach(new Consumer<String>() {
			public void accept(String actionName) {
				Action action = authDao.getActionByItsName(actionName);
				if ( !authDao.userCanDoAction(currentUserToken, action) ) {
					userIsGranted = false;
				} else userIsGranted = true;
			}
		});
		logger.info("userCanDoListOfActions : user is granted");
		return userIsGranted;
	}
	
	public boolean myUserAccount (String token, User user) {		
		
		User currentUser = authDao.getUserByToken(token);
		
		if ( currentUser != null && currentUser.getId() == user.getId() ) {
			return true;
			
		} else return false;
		
	}
	
}