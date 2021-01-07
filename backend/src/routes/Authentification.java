package routes;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controllers.ControllerUser;
import models.User;
import security.JWTokenUtility;
import security.SigninNeeded;

import javax.ws.rs.core.SecurityContext;

@Path("/")
public class Authentification {

	@GET
	@SigninNeeded
	@Path("/whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public Response whoami(@Context SecurityContext security) {
		String username = security.getUserPrincipal().getName();
		User user = ControllerUser.getUser(username);
		if (user!=null)
			return Response.ok().entity(user).build();
		return Response.status(Status.NO_CONTENT).build();
	}



	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signin(@QueryParam("email") String login, @QueryParam("password") String password) {
		User u = ControllerUser.login(login, password);

		if (u != null)
			return Response.ok().entity(JWTokenUtility.buildJWT(u.getLogin())).build();

		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(@QueryParam("email") String login, @QueryParam("password") String password,
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname) {
		if (ControllerUser.createUser(login, password, firstname, lastname))
			return Response.status(Status.CREATED).build();
		return Response.status(Status.CONFLICT).build();

	}

	/**
	 * Méthode permettant de récupérer l'ensemble des roles d'un utilisateur
	 * 
	 * @param user l'utilisateur
	 * @return une liste de tous les roles associés à l'utilisateur user
	 */
	public static List<String> findUserRoles(String user) {
		return null;
	}
}