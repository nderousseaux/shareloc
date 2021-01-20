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
import controllers.ControllerTask;
import controllers.ControllerService;
import models.User;
import security.JWTokenUtility;
import security.SigninNeeded;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.SecurityContext;

@Path("/service")
public class Service {
	
	@GET
	@SigninNeeded
	@Path("/colocation/{idColoc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListService(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			return Response.ok().entity(ControllerService.getServiceWaiting(coloc, user)).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@SigninNeeded
	@Path("/{idService}/{isValide}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateService(@Context SecurityContext security, @PathParam("idService") int idService, @PathParam("isValide") Boolean isValide) {
		
		models.Service s = ControllerService.getService(idService);
		
		if(s.getIsValide() != null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		//On regarde si l'user fait partit de la liste des bénéficiaires
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerService.isBeneficiaire(user, s)) {
			ControllerService.acceptService(s, isValide);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

}
