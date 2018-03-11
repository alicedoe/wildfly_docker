package com.alicegabbana.restserver.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.alicegabbana.restserver.dao.AdminDao;
import com.alicegabbana.restserver.model.Setting;
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

}
