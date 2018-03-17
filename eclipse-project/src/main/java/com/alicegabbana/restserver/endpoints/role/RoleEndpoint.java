package com.alicegabbana.restserver.endpoints.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.model.Role;
import com.alicegabbana.restserver.service.AuthService;
import com.alicegabbana.restserver.service.RoleService;

@Path("/role")
public class RoleEndpoint {
	
	@EJB
	RoleService roleService;
	
	@EJB
	AuthService authService;
	
	Logger logger = Logger.getLogger(RoleEndpoint.class);

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRole(Role role, @HeaderParam("UserToken") String userToken) {
		
		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"create role"
	            		));
		Response addRoleServiceResponse = roleService.createRole(userToken, actionsNeeded, role);
		return addRoleServiceResponse;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRole(Role role, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));		
		Response getRoleServiceResponse = roleService.getRole(userToken, actionsNeeded, role);
		return getRoleServiceResponse;
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRole(@HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"read role"
	            		));
		Response getAllRoleServiceResponse = roleService.getAllRole(userToken, actionsNeeded);
		return getAllRoleServiceResponse;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRole(Role role, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"delete role"
	            		));
		Response deleteRoleResponse = roleService.deleteRole(userToken, actionsNeeded, role);
		return deleteRoleResponse;
	}
	
	@PUT
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editRole(Role role, @HeaderParam("UserToken") String userToken) {

		List<String> actionsNeeded = new ArrayList<String>(
	            Arrays.asList(
	            		"update role"
	            		));
		Response updateRoleServiceResponse = roleService.updateRole(userToken, actionsNeeded, role);
		return updateRoleServiceResponse;
	}

}
