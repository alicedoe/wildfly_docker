package com.alicegabbana.cahierenligne.services.utils;

import javax.ejb.Local;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Local
public interface AuthServiceLocal {

	String createAndReturnToken(String email)  throws KeyLengthException, JOSEException;
}
