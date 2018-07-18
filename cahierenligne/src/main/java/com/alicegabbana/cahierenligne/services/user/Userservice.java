package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.setting.SettingServiceLocal;
import com.alicegabbana.cahierenligne.services.utils.AuthServiceLocal;
import com.alicegabbana.cahierenligne.services.utils.PasswordUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

@Stateless
public class Userservice implements UserServiceLocal, UserServiceRemote {

	private static final long serialVersionUID = 8563372372793166338L;
	
	@EJB
	AuthServiceLocal authService;
	
	@EJB
	SettingServiceLocal settingService;

	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(Userservice.class);
	
	public User create(User user) {
		String token = returnTokenUserByEmail(user.getEmail());
		String hashPwd = hashPassword(user.getPwd());
		user.setPwd(hashPwd);
		user.setToken(token);
		if (userIsComplete(user)) {
			User usercreated = em.merge(user);
			return usercreated;
		}
		logger.fatal("User is not complete : "+user.toString());
		throw new NullPointerException();
	}
	
	private Boolean userIsComplete(User user) {
		if ( user.getRole() == null 
				|| user.getName() == null 
				|| user.getFirstname() == null 
				|| user.getEmail() == null
				|| user.getToken() == null 
				|| user.getPwd() == null ) {
			return false;
		}
		return true;
	}
	
	public String hashPassword(String password) {
        String salt = getSalt();
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
		return mySecurePassword;
	}
	
	public String getSalt() {		
		String salt = settingService.get("PASS_SALT").getParam();
		if ( salt == null) return null;
		return salt;		
	}
	
	public String returnTokenUserByEmail (String email) {
		try {
			String token = authService.createAndReturnToken(email);
			return token;
		} catch (KeyLengthException e) {
			e.printStackTrace();
			return null;
		} catch (JOSEException e) {
			e.printStackTrace();
			return null;
		}
	}
}
