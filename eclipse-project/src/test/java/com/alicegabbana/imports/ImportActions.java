package com.alicegabbana.imports;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.testcontext.TestContextAbstract;

public class ImportActions extends TestContextAbstract {
	
	@Test
	public void createAllActions()  {
		
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
		
		Action action = new Action();
		for (String actionName : actionsNames) {
			action.setName(actionName);			
			actionService.create(action);
		}
		
	}

}
