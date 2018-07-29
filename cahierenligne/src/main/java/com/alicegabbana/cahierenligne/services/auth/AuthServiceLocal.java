package com.alicegabbana.cahierenligne.services.auth;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.alicegabbana.cahierenligne.services.action.ActionException;
import com.alicegabbana.cahierenligne.services.setting.SettingException;
import com.alicegabbana.cahierenligne.services.user.UserException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Local
public interface AuthServiceLocal {

	String createAndReturnToken(String email)  throws KeyLengthException, JOSEException, SettingException;
	boolean userHasActionList (String token, List<String> actions) throws UserException, ActionException;
	Response returnResponse (int status);
	Response returnResponse (int status, Object entity);
}
