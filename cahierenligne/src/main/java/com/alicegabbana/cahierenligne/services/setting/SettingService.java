package com.alicegabbana.cahierenligne.services.setting;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.entities.Setting;
import com.alicegabbana.cahierenligne.services.action.ActionService;

@Stateless
public class SettingService implements SettingServiceLocal, SettingServiceRemote {

	private static final long serialVersionUID = -6244705257010130363L;

	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	Logger logger = Logger.getLogger(ActionService.class);

	public Setting create( Setting setting ) {		
		if (settingIsComplete(setting)) {
			Setting loadedSetting = em.merge(setting);
			return loadedSetting;
		}
		logger.fatal("Setting is not complete : "+setting.toString());
		throw new NullPointerException();		
	}
	
	private Boolean settingIsComplete(Setting setting) {
		if ( setting.getName() == null 
				|| setting.getParam() == null ) {
			return false;
		}
		return true;
	}
	
	public Setting get(String name) {		
		Setting setting = em.find(Setting.class, name);
		if (setting == null) {
			logger.fatal("Setting "+name+" Not found !");
			throw new NullPointerException();		
		}
		return setting;
	}

}
