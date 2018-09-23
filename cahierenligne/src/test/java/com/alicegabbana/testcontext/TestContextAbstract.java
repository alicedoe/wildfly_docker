package com.alicegabbana.testcontext;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;

import com.alicegabbana.cahierenligne.services.action.ActionServiceRemote;
import com.alicegabbana.cahierenligne.services.role.RoleServiceRemote;
import com.alicegabbana.cahierenligne.services.school.SchoolServiceRemote;
import com.alicegabbana.cahierenligne.services.setting.SettingServiceRemote;
import com.alicegabbana.cahierenligne.services.town.TownServiceRemote;
import com.alicegabbana.cahierenligne.services.user.UserServiceRemote;

public abstract class TestContextAbstract {

	static Properties props;
	static InitialContext ctx;
	
	public static String JNDI_HOST = "172.17.0.3";
	public static String JNDI_PORT = "8080";
	
	public static ActionServiceRemote actionService;
	public static RoleServiceRemote roleService;
	public static UserServiceRemote userService;
	public static SettingServiceRemote settingService;
	public static TownServiceRemote townService;
	public static SchoolServiceRemote schoolService;
	
	static String JNDI_DEPLOYMENT_UNIT 			= "application";
	
	static String JNDI_SERVICE_ACTION 				= "/ActionService!com.alicegabbana.cahierenligne.services.action.ActionServiceRemote";
	static String JNDI_SERVICE_ROLE 				= "/RoleService!com.alicegabbana.cahierenligne.services.role.RoleServiceRemote";
	static String JNDI_SERVICE_USER				= "/Userservice!com.alicegabbana.cahierenligne.services.user.UserServiceRemote";
	static String JNDI_SERVICE_SETTING				= "/SettingService!com.alicegabbana.cahierenligne.services.setting.SettingServiceRemote";
	static String JNDI_SERVICE_TOWN				= "/TownService!com.alicegabbana.cahierenligne.services.town.TownServiceRemote";
	static String JNDI_SERVICE_SCHOOL				= "/SchoolService!com.alicegabbana.cahierenligne.services.school.SchoolServiceRemote";

	@BeforeClass
	public static void setup() throws Exception {
		try {	
				System.out.println("Initialize Service Context...");
				if (ctx == null) {
					props = new Properties();
					props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
					props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
					props.put(Context.PROVIDER_URL, "http-remoting://" + JNDI_HOST + ":" + JNDI_PORT);
					props.put(Context.SECURITY_PRINCIPAL, "remoteTest");
					props.put(Context.SECURITY_CREDENTIALS, "remoteTest");
					props.put("jboss.naming.client.ejb.context", true);

					ctx = new InitialContext(props);
					System.out.println("Context created.");

					System.out.println("Getting remote services endpoints...");					
					actionService = (ActionServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_ACTION);
					roleService = (RoleServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_ROLE);
					userService = (UserServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_USER);
					settingService = (SettingServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_SETTING);
					townService = (TownServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_TOWN);
					schoolService = (SchoolServiceRemote)
							getContext(JNDI_DEPLOYMENT_UNIT+JNDI_SERVICE_SCHOOL);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getContext(String ejbName) {
		try {
			return ctx.lookup(ejbName);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
}