package com.alicegabbana.testcontext;

public interface JndiResources {
	
	String JNDI_DEPLOYMENT_UNIT 			= "application";
	
	String JNDI_SERVICE_ACTION 				= "/ActionService!com.alicegabbana.restserver.services.action.ActionServiceRemote";
}