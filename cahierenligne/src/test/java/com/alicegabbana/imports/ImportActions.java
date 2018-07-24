package com.alicegabbana.imports;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.alicegabbana.cahierenligne.entities.Action;
import com.alicegabbana.cahierenligne.entities.Role;
import com.alicegabbana.cahierenligne.entities.Setting;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.user.UserException;
import com.alicegabbana.testcontext.TestContextAbstract;

public class ImportActions extends TestContextAbstract {
	
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
			"delete all user"
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
			Action action = actionService.get(actionName);
			actions.add(action);
		}
		role.setName("admin");
		role.setActions(actions);
		roleService.create(role);
	}
	
	@Test
	public void user_030_createSetting()  {
		Setting setting = new Setting();
		setting.setName("TOKEN_EXP");
		setting.setParam("1500");
		settingService.create(setting);
		
		setting.setName("API_KEY");
		setting.setParam("20e9b1b2-e17f-4667-8656-9899aafedbda");
		settingService.create(setting);
		
		setting.setName("PASS_SALT");
		setting.setParam("9899aafedbda");
		settingService.create(setting);
		
	}
	
	@Test
	public void user_040_createAdminUser()  {
		Role adminRole = new Role();
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
	}

}
