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
import com.alicegabbana.restserver.model.User;
import com.alicegabbana.restserver.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.alicegabbana.restserver.dao.ActionDao;
import com.alicegabbana.restserver.dao.AdminDao;
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
	
	@EJB
	AdminDao adminDao;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(CreateDumpEndpoint.class);
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String ping() { 
		
		
		try {
			return authService.createToken("toto@toto.com");
		} catch (KeyLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
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
		Role adminRole = roleDao.create(admin);
		
//		Dump Users
		User userAdmin = new User();
		userAdmin.setEmail("admin@admin.com");
		userAdmin.setNom("admin");
		userAdmin.setPrenom("admin");
		userAdmin.setPwd("admin");
		userAdmin.setRole(adminRole);
		userAdmin.setToken("monsupertoken");
		userDao.create(userAdmin);

		return "Dump created !";
		
	}

}
