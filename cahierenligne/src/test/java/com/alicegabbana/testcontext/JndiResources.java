package com.alicegabbana.testcontext;

public interface JndiResources {
	
	String JNDI_DEPLOYMENT_UNIT 			= "application";
	
	String JNDI_SERVICE_ACTION 				= "/ActionService!com.alicegabbana.cahierenligne.services.action.ActionServiceRemote";
	String JNDI_SERVICE_ROLE 				= "/RoleService!com.alicegabbana.cahierenligne.services.role.RoleServiceRemote";
	String JNDI_SERVICE_USER				= "/Userservice!com.alicegabbana.cahierenligne.services.user.UserServiceRemote";
	String JNDI_SERVICE_SETTING				= "/SettingService!com.alicegabbana.cahierenligne.services.setting.SettingServiceRemote";
	String JNDI_SERVICE_TOWN				= "/TownService!com.alicegabbana.cahierenligne.services.town.TownServiceRemote";
	String JNDI_SERVICE_SCHOOL				= "/SchoolService!com.alicegabbana.cahierenligne.services.school.SchoolServiceRemote";
}