package com.alicegabbana.testcontext;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;

import com.alicegabbana.restserver.services.action.ActionServiceRemote;
import com.alicegabbana.restserver.services.action.TotoRemote;

public abstract class TestContextAbstract {

	static Properties props;
	static InitialContext ctx;
	
	public static String JNDI_HOST = "172.17.0.3";
	public static String JNDI_PORT = "8080";
	
	public static ActionServiceRemote actionService;

	@BeforeClass
	public static void setup() throws Exception {
		try {	
				System.out.println("Initialize Service Context...");
				if (ctx == null) {
					props = new Properties();
					props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
					props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
					props.put(Context.PROVIDER_URL, "http-remoting://" + JNDI_HOST + ":" + JNDI_PORT);
					props.put(Context.SECURITY_PRINCIPAL, "remoteTest");
					props.put(Context.SECURITY_CREDENTIALS, "remoteTest");
					props.put("jboss.naming.client.ejb.context", true);

					ctx = new InitialContext(props);
					System.out.println("Context created.");

					System.out.println("Getting remote services endpoints...");					
					actionService = (ActionServiceRemote)
							getContext(JndiResources.JNDI_DEPLOYMENT_UNIT+JndiResources.JNDI_SERVICE_ACTION);
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
