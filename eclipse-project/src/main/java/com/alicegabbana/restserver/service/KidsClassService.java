package com.alicegabbana.restserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Status;

import com.alicegabbana.restserver.dao.KidsClassDao;
import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dto.RoleDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Role;
import com.alicegabbana.restserver.entity.User;

@Stateless
public class KidsClassService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	KidsClassDao kidsClassDao;
	
	Logger logger = Logger.getLogger(KidsClassService.class);
	
	public KidsClass getKidsClassByName (String name) {
		return kidsClassDao.getKidsClassByName(name);
	}
	
}
