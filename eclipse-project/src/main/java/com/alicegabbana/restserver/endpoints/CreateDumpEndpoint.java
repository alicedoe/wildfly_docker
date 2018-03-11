package com.alicegabbana.restserver.endpoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.model.User;
import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dao.AuthDao;
import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dao.UserDao;

@Path("/dump")
public class CreateDumpEndpoint {
	
	@EJB
	RoleDao roleDao;
	
	@EJB
	ActionDao actionDao;
	
	@EJB
	UserDao userDao;
	
	Logger logger = Logger.getLogger(CreateDumpEndpoint.class);

	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String dump() {
		
//		Dump Actions
		Action addRole = new Action();
		addRole.setNom("add role");
		Action addRoleAction = actionDao.create(addRole);

//		Dump Roles
		Role admin = new Role();
		admin.setNom("admin");
		List<Action> adminActions = new ArrayList<Action>();
		adminActions.add(addRoleAction);
		admin.setActions(adminActions);
		Role adminRole = roleDao.create(admin);
		
//		Dump Users
		User userAdmin = new User();
		userAdmin.setEmail("admin@admin.com");
		userAdmin.setNom("admin");
		userAdmin.setPrenom("admin");
		userAdmin.setPwd("admin");
		userAdmin.setRole(adminRole);
		userAdmin.setToken("monsupertoken");
		Date currentDate = new Date();
		userAdmin.setTokenCreation(currentDate);
		userDao.create(userAdmin);
		
		return "Dump created !";
		
	}

}
