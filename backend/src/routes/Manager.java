package routes;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controllers.ControllerColocation;
import controllers.ControllerUser;
import controllers.ControllerManager;
import models.User;
import security.JWTokenUtility;
import security.SigninNeeded;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.SecurityContext;

@Path("/colocation/{idColoc}/manager")
public class Manager {
	
	@GET
	@SigninNeeded
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
		
		
		if(ControllerColocation.isInColocation(user, coloc))
			return Response.ok().entity(ControllerManager.getManagers(coloc.getId())).build();
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
	
	@PUT
	@SigninNeeded
	@Path("/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response upToManager(@Context SecurityContext security, @PathParam("idColoc") int idColoc,  @PathParam("email") String email) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
		
		User futurManager = ControllerUser.getUser(email);
		
		if(ControllerColocation.isManagerInColocation(user, coloc) && ControllerColocation.isInColocation(futurManager, coloc)) {
			ControllerManager.upToManager(coloc, futurManager);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
	
	@DELETE
	@SigninNeeded
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response downgrade(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
				
		if(ControllerColocation.isManagerInColocation(user, coloc) && ControllerManager.nbManager(coloc)>1) {
			ControllerManager.downgrade(coloc, user);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
		
	}

}
