package com.alicegabbana.restserver.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Setting;

@Stateless
public class AdminDao {
	
	Logger logger = Logger.getLogger(AdminDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Setting create( Setting setting ) {		
		
		if ( setting.getId() == null ) {
			String message = "Id must be defined to create a setting : " + setting;
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		Setting loadedSetting = em.merge(setting);
		return loadedSetting;
		
	}
	
	public Setting getParam(String name) {
		Setting token = em.find(Setting.class, name);
		return token;
	}

}
