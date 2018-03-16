package com.alicegabbana.restserver.endpoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.model.Setting;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.RoleService;
import com.alicegabbana.restserver.service.UserService;
import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dao.AdminDao;

@Path("/dump")
public class CreateDumpEndpoint {
	
	@EJB
	ActionDao actionDao;
	
	@EJB
	AdminDao adminDao;
	
	@EJB
	AuthService authService;
	
	@EJB
	UserService userService;
	
	@EJB
	RoleService roleService;
	
	Logger logger = Logger.getLogger(CreateDumpEndpoint.class);
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean ping() { 
	
			return userService.validateEmail("totototo.com");
		
	}

	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String dump() {

//		Dump Settings
		adminDao.create( new Setting("API_KEY" , "e7a8d311-8e16-441b-9f0a-e78b62cb34b5") );
		adminDao.create( new Setting("TOKEN_EXP" , "150") );
		
//		Dump Actions
		List<String> listActions   = new ArrayList<String>(
		            Arrays.asList(
		            		"create role", 
		            		"delete role", 
		            		"update role", 
		            		"read role"
		            		));
		listActions.forEach(new Consumer<String>() {
			public void accept(String actionName) {
				Action action = new Action();				
				action.setNom(actionName);
				actionDao.create(action);
			}
		});

//		Dump Roles
		Role admin = new Role();
		admin.setNom("admin");
		List<Action> adminActions = new ArrayList<Action>();		
		adminActions.add(actionDao.getAction("create role"));
		adminActions.add(actionDao.getAction("delete role"));
		adminActions.add(actionDao.getAction("update role"));
		adminActions.add(actionDao.getAction("read role"));
		admin.setActions(adminActions);
//		Role adminRole = roleService.createRole(admin);
		
//		Dump Users
//		User userAdmin = new User();
//		userAdmin.setEmail("admin@admin.com");
//		userAdmin.setNom("admin");
//		userAdmin.setPrenom("admin");
//		userAdmin.setPwd("admin");
//		userAdmin.setRole(adminRole);
//		userAdmin.setToken("monsupertoken");
//		userService.createUser(userAdmin);
//
		return "Dump created !";
		
	}

}
