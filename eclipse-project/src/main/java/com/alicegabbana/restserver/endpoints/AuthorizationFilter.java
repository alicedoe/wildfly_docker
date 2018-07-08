package com.alicegabbana.restserver.endpoints;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import com.alicegabbana.restserver.services.AuthService;
import com.nimbusds.jose.util.IOUtils;



@Provider
@Actions
public class AuthorizationFilter implements ContainerRequestFilter {
	
	@EJB
	AuthService authService;
	
	@Context
    private ResourceInfo resourceInfo;
	
	List<String> actionsNeeded = new ArrayList<String>();

  @Override
  public void filter(ContainerRequestContext reqContext) throws IOException {
	  Actions annotations = resourceInfo.getResourceMethod().getAnnotation(Actions.class);
	  
	  System.out.println("--request headers-----");
      MultivaluedMap<String, String> headers = reqContext.getHeaders();
      for (Entry<String, List<String>> string : headers.entrySet()) {
		System.out.println("tzst : "+string);
	}

       
	  if (reqContext.getMethod() == "PUT") {
		  
	  }
	  String token = reqContext.getHeaders().get("token").get(0);
	  for (String annotation : annotations.value()) {
		  actionsNeeded.add(annotation);
	  }
	  
		if (authService.userHasActionList(token, actionsNeeded) == false ) 
		{
			reqContext.abortWith(authService.returnResponse(401));
			return;
		}
  }
  
//  private String getEntityBody(ContainerRequestContext requestContext) {
//	  ByteArrayOutputStream out = new ByteArrayOutputStream();
//	  InputStream in = requestContext.getEntityStream();
//
//	  String result = null;
//	  try {
//	    ReaderWriter.writeTo(in, out);
//	    IOUtils.copy(in, writer, encoding);	    
//
//	    byte[] requestEntity = out.toByteArray();
//	    if (requestEntity.length == 0) {
//	      result = "";
//	    } else {
//	      result = new String(requestEntity, "UTF-8");
//	    }
//	    requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
//
//	  } catch (IOException e) {
//	  }
//	  return result;
//	}
}