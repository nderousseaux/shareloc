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
import models.User;
import models.Task;
import security.JWTokenUtility;
import security.SigninNeeded;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.SecurityContext;

@Path("/colocation")
public class Colocation {
	

	@GET
	@SigninNeeded
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColocations(@Context SecurityContext security) {
		
		List<models.Colocation> colocs = ControllerColocation.getColocations(security.getUserPrincipal().getName());
		
		if (colocs.size() != 0)
			return Response.ok().entity(colocs).build();
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	@SigninNeeded
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createColocation(@Context SecurityContext security, models.Colocation coloc) {
		
		if (ControllerColocation.create(ControllerUser.getUser(security.getUserPrincipal().getName()), coloc))
			return Response.ok().build();
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}
	
	@GET
	@SigninNeeded
	@Path("/{idColoc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
		
		
		if(ControllerColocation.isInColocation(user, coloc))
			return Response.ok().entity(coloc).build();
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
	
	@POST
	@SigninNeeded
	@Path("/{idColoc}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc, models.Colocation colocation) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isManagerInColocation(user, coloc)) {
			ControllerColocation.modifyColoc(idColoc, colocation);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@SigninNeeded
	@Path("/{idColoc}/quit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response quitColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			if(ControllerColocation.quit(user, coloc))
				return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@DELETE
	@SigninNeeded
	@Path("/{idColoc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isManagerInColocation(user, coloc)) {
			ControllerColocation.deleteColocation(coloc);
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@SigninNeeded
	@Path("/{idColoc}/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersByColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc, models.Colocation colocation) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			return Response.ok().entity(ControllerColocation.getUserFromColoc(coloc.getId())).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@SigninNeeded
	@Path("/{idColoc}/user/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUserToColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc, @PathParam("email") String email) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		User futurUser = ControllerUser.getUser(email);

		if(ControllerColocation.isManagerInColocation(user, coloc)) {
			if(!ControllerColocation.isInColocation(futurUser, coloc)) {
				ControllerColocation.addUser(coloc, futurUser);
				return Response.ok().build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@DELETE
	@SigninNeeded
	@Path("/{idColoc}/user/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeUserToColoc(@Context SecurityContext security, @PathParam("idColoc") int idColoc, @PathParam("email") String email) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		User futurUser = ControllerUser.getUser(email);


		if(ControllerColocation.isManagerInColocation(user, coloc)) {
			if(ControllerColocation.isInColocation(futurUser, coloc)) {
				if(!ControllerColocation.isManagerInColocation(futurUser, coloc)) {
					ControllerColocation.delUser(coloc, futurUser);
					return Response.ok().build();
				}
			}
			
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@SigninNeeded
	@Path("/{idColoc}/task")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@Context SecurityContext security, @PathParam("idColoc") int idColoc) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			return Response.ok().entity(ControllerTask.getTasks(coloc)).build();

		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	
	@PUT
	@SigninNeeded
	@Path("/{idColoc}/task")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTask(@Context SecurityContext security, @PathParam("idColoc") int idColoc, Task task) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			ControllerTask.createTask(coloc, task, user);
			return Response.ok().build();

		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@SigninNeeded
	@Path("/{idColoc}/task/state")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTaskByState(@Context SecurityContext security, @PathParam("idColoc") int idColoc, @QueryParam("state") String state) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());

		if(ControllerColocation.isInColocation(user, coloc)) {
			return Response.ok().entity(ControllerTask.findByStatus(state, coloc)).build();

		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@SigninNeeded
	@Path("/{idColoc}/{email}/points")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPoints(@Context SecurityContext security, @PathParam("idColoc") int idColoc, @PathParam("email") String email) {
		
		models.Colocation coloc = ControllerColocation.getColocation(idColoc);
		if (coloc == null)
			return Response.status(Status.NO_CONTENT).build();
		
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
		User userb = ControllerUser.getUser(email);

		if(ControllerColocation.isInColocation(user, coloc) && ControllerColocation.isInColocation(userb, coloc)) {
			return Response.ok().entity(ControllerColocation.getUserColocation(userb, coloc).getPoints()).build();

		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	
	

}
