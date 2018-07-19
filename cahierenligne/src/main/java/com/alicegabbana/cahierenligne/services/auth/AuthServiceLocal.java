package com.alicegabbana.cahierenligne.services.auth;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Local
public interface AuthServiceLocal {

	String createAndReturnToken(String email)  throws KeyLengthException, JOSEException;
	boolean userHasActionList (String token, List<String> actions);
	Response returnResponse (int status);
	Response returnResponseWithEntity (int status, Object entity);
}
