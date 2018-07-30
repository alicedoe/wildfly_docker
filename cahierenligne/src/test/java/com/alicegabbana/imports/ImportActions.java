package com.alicegabbana.imports;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alicegabbana.cahierenligne.entities.Action;
import com.alicegabbana.cahierenligne.entities.Role;
import com.alicegabbana.cahierenligne.entities.Setting;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.action.ActionException;
import com.alicegabbana.cahierenligne.services.role.RoleException;
import com.alicegabbana.cahierenligne.services.setting.SettingException;
import com.alicegabbana.cahierenligne.services.user.UserException;
import com.alicegabbana.testcontext.TestContextAbstract;

public class ImportActions extends TestContextAbstract {
	
	Logger logger = Logger.getLogger(ImportActions.class);
	
	List<String> actionsNames = Arrays.asList(
			"create level", 
			"update all level", 
			"delete all level",
			"create school", 
			"update all school", 
			"delete all school",
			"create homework", 
			"update all homework", 
			"delete all homework",
			"create town", 
			"update all town", 
			"delete all town",
			"create kidsClass", 
			"update all kidsClass", 
			"delete all kidsClass",
			"create subject", 
			"update all subject", 
			"delete all subject",
			"create tag", 
			"update all tag", 
			"delete all tag",
			"create user", 
			"update all user", 
			"delete all user",
			"read all user"
			);	
	
	@Test
	public void user_010_createAllActions()  {	
		
		Action action = new Action();
		for (String actionName : actionsNames) {
			action.setName(actionName);			
			actionService.create(action);
		}		
	}
	
	@Test
	public void user_020_createAdminRole()  {
		Role role = new Role();
		List<Action> actions = new ArrayList<Action>();
		for (String actionName : actionsNames) {		
			try {
				Action action = actionService.get(actionName);
				actions.add(action);
			} catch (ActionException e) {
				logger.error(e.getMessage());
			}						
		}
		role.setName("admin");
		role.setActions(actions);
		try {
			roleService.create(role);
		} catch (RoleException e) {
			logger.error(e.getCode()+" : "+e.getMessage());
		}
		
	}
	
	@Test
	public void user_030_createSetting()  {
		Setting setting = new Setting();
		setting.setName("TOKEN_EXP");
		setting.setParam("1500");
		try {
			settingService.create(setting);
		} catch (SettingException e) {
			logger.error(e.getMessage());
		}		
		
		setting.setName("API_KEY");
		setting.setParam("20e9b1b2-e17f-4667-8656-9899aafedbda");
		try {
			settingService.create(setting);
		} catch (SettingException e) {
			logger.error(e.getMessage());
		}
		
		setting.setName("PASS_SALT");
		setting.setParam("9899aafedbda");
		try {
			settingService.create(setting);
		} catch (SettingException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@Test
	public void user_040_createAdminUser() throws SettingException  {
		Role adminRole = new Role();
		try {
			adminRole = roleService.get("admin");
			User adminUser = new User();
			adminUser.setFirstname("prenom admin");
			adminUser.setEmail("admin@admin.com");
			adminUser.setRole(adminRole);
			adminUser.setName("nom admin");
			adminUser.setPwd("password");
			try {
				userService.create(adminUser);
			} catch (UserException e) {
				e.printStackTrace();
			}
		} catch (RoleException e) {
//			logger.error("Role does not exist !");
		}		
	}

}
