package com.alicegabbana.cahierenligne.services.user;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	public User create(User user) {
		String token = returnTokenUserByEmail(user.getEmail());
		if (token == null) return null;
		String hashPwd = hashPassword(user.getPwd());
		user.setPwd(hashPwd);
		user.setToken(token);
		User usercreated = em.merge(user);
		return usercreated;
	}
	
	public String hashPassword(String password) {		
		System.out.println("password : "+password);
        String salt = getSalt();
        System.out.println("salt : "+salt);
        String mySecurePassword = PasswordUtils.generateSecurePassword(password, salt);
        System.out.println("mysecure : "+mySecurePassword);
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
