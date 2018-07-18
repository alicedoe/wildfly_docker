package com.alicegabbana.cahierenligne.services.utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.alicegabbana.cahierenligne.services.setting.SettingServiceLocal;
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

	public String createAndReturnToken(String email) throws KeyLengthException, JOSEException {
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
		
	}

}
