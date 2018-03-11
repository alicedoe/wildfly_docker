package com.alicegabbana.restserver.endpoints.role;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Action;
import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.dao.AuthDao;
import com.alicegabbana.restserver.dao.RoleDao;
import com.alicegabbana.restserver.dao.UserDao;

@Path("/role")
public class RoleEndpoint {
	
	@EJB
	RoleDao roleDao;
	
	@EJB
	AuthDao authDao;
	
	Logger logger = Logger.getLogger(RoleEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Role addRole(Role role, @HeaderParam("UserToken") String userToken) {

		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Post : /role/add");
		}
		
		String actionNeeded = "add role";
		Action authAction = authDao.getAction(actionNeeded);
		logger.error("we are if user can do action : "+authAction.getId());
		if ( !authDao.userCan(authAction, userToken) ) {
			
			throw new SecurityException("Your role doesn't allowed you to perfom this action : "+actionNeeded);
			
		} else {
			
			logger.info("Auth granted");
			
		}
		
//		Role newRole = roleDao.create(role);		
		return role;
	}
	
	@GET
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRole() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Get : /role/{roleId}");
		}
		
		return "User !";
	}
	
	@DELETE
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteRole() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Delete : /user/{roleId}");
		}
		
		return "User !";
	}
	
	@PUT
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editRole() {	
		
		if (logger.isDebugEnabled()) {
			logger.debug("Endpoint Put : /role/edit");
		}
		
		return "Edited !";
	}

}
