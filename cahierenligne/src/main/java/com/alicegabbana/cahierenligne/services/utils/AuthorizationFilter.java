package com.alicegabbana.cahierenligne.services.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.alicegabbana.cahierenligne.services.auth.AuthServiceLocal;

@Provider
@Actions
public class AuthorizationFilter implements ContainerRequestFilter {
	
	@EJB
	AuthServiceLocal authService;
	
	@Context
	private ResourceInfo resourceInfo;
	
	List<String> actionsNeeded = new ArrayList<String>();

  @Override
  public void filter(ContainerRequestContext reqContext) throws IOException {
	  Actions annotations = resourceInfo.getResourceMethod().getAnnotation(Actions.class);
	  String token;

      try {
    	  token = reqContext.getHeaders().get("token").get(0);	
    	  for (String annotation : annotations.value()) {
    		  actionsNeeded.add(annotation);
    	  }
	    	if (authService.userHasActionList(token, actionsNeeded) == false ) 
	    	{
	    		reqContext.abortWith(authService.returnResponse(401));
	    		return;
	    	}
		} catch (Exception e) {
			System.out.println("Headers 'token' does not exist !");
			reqContext.abortWith(authService.returnResponse(400));
		}    
	  
  }
}