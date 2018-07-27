package com.alicegabbana.cahierenligne.services.auth;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.alicegabbana.cahierenligne.entities.Action;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.action.ActionException;
import com.alicegabbana.cahierenligne.services.action.ActionServiceLocal;
import com.alicegabbana.cahierenligne.services.setting.SettingException;
import com.alicegabbana.cahierenligne.services.setting.SettingServiceLocal;
import com.alicegabbana.cahierenligne.services.user.UserServiceLocal;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Stateless
public class AuthService implements AuthServiceLocal, AuthServiceRemote {

	private static final long serialVersionUID = 8765743400866737972L;
	
	@EJB
	SettingServiceLocal settingService;
	
	@EJB
	ActionServiceLocal actionService;
	
	@EJB
	UserServiceLocal userService;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	boolean userIsGranted;

	public String createAndReturnToken(String email) throws KeyLengthException, JOSEException, SettingException {
		
		try {
			int tokenDelay = Integer.parseInt(settingService.get("TOKEN_EXP").getParam());
			String api_key = settingService.get("API_KEY").getParam();
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
		} catch (Exception e) {
			throw new SettingException(e.getMessage());
		}		
		
	}
	
	public boolean userHasActionList (String token, List<String> actions) {		

		final String currentUserToken = token;
		
		actions.forEach(new Consumer<String>() {
			public void accept(String actionName) {
				try {
					Action action = actionService.get(actionName);
					if ( !userHasThisAction(currentUserToken, action) ) {
						userIsGranted = false;
					} else userIsGranted = true;
				} catch (ActionException e) {
					e.printStackTrace();
				}				
			}
		});
		return userIsGranted;
	}

	private boolean userHasThisAction( String token, Action action ) {		
		
		User currentUser;
		
		currentUser = userService.getByToken(token);
		
		if ( currentUser != null ) {
			if ( !currentUser.getRole().getActions().contains(action) ) {
				return false;
			}
		} else {
			return false;
		}
		return true;
		
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
