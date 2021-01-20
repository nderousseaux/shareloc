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

@Path("/task")
public class Task {
	
	@GET
	@SigninNeeded
	@Path("/{idTask}/state")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStateTask(@Context SecurityContext security, @PathParam("idTask") int idTask) {
		models.Task t = ControllerTask.getTask(idTask);
		
		models.Colocation coloc = ControllerTask.getColocByTask(t);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			return Response.ok().entity(ControllerTask.getStatus(t)).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

	@PUT
	@SigninNeeded
	@Path("/{idTask}/voteAdd/{isAccept}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStateTask(@Context SecurityContext security, @PathParam("idTask") int idTask, @PathParam("isAccept") int accept) {
		boolean isAccept = !(accept==0);
		
		models.Task t = ControllerTask.getTask(idTask);
		
		//Possible que si l'état de la tache est VOTINGCREATING
		if(!ControllerTask.getStatus(t).equals("VOTINGCREATING")) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		models.Colocation coloc = ControllerTask.getColocByTask(t);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			ControllerTask.voteAdd(user, t, isAccept);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@SigninNeeded
	@Path("/{idTask}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(@Context SecurityContext security, @PathParam("idTask") int idTask, List<User> beneficiaires) {
		models.Task t = ControllerTask.getTask(idTask);
		
		//Possible que si l'état de la tache est ACTIVE 
		if(!ControllerTask.getStatus(t).equals("ACTIVE")) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		models.Colocation coloc = ControllerTask.getColocByTask(t);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		//On vérifie que l'user n'est pas dans la liste des bénificiaires
		for(User u : beneficiaires) {
			if(u.getEmail() == user.getEmail()) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}
		
		if(ControllerColocation.isInColocation(user, coloc)) {
			ControllerService.createService(t, user, beneficiaires);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}


}
