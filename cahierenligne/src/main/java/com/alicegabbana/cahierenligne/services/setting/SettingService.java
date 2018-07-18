package com.alicegabbana.cahierenligne.services.setting;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alicegabbana.cahierenligne.entities.Setting;

@Stateless
public class SettingService implements SettingServiceLocal, SettingServiceRemote {

	private static final long serialVersionUID = 166347038186308495L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Setting create( Setting setting ) {		
		
		if ( setting.getName() == null ) {
			String message = "Id must be defined to create a setting : " + setting;
			throw new IllegalArgumentException(message);
		}
		Setting loadedSetting = em.merge(setting);
		return loadedSetting;
		
	}
	
	public Setting get(String name) {
		try {
			Setting setting = em.find(Setting.class, name);
			return setting;
		} catch (IllegalArgumentException e) {
			System.out.println("Setting "+name+" does not exist");
		}		
		return null;
	}

}
