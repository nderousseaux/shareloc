package routes;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controllers.ControllerColocation;
import controllers.ControllerUser;
import models.User;
import security.JWTokenUtility;
import security.SigninNeeded;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.SecurityContext;

@Path("/")
public class Authentification {

	@GET
	@SigninNeeded
	@Path("/whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public Response whoami(@Context SecurityContext security) {
		User user = ControllerUser.getUser(security.getUserPrincipal().getName());
		if (user != null)
			return Response.ok().entity(user).build();
		return Response.status(Status.NO_CONTENT).build();
	}




	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signin(@QueryParam("email") String login, @QueryParam("password") String password) {
		String sha256hex = DigestUtils.sha256Hex(password);
		User u = ControllerUser.login(login, sha256hex);

		if (u != null)
			return Response.ok().entity(JWTokenUtility.buildJWT(u.getEmail())).build();

		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@PUT
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(@QueryParam("email") String login, @QueryParam("password") String password,
			@QueryParam("firstname") String firstname,  @QueryParam("lastname") String lastname) {
		
		String sha256hex = DigestUtils.sha256Hex(password);
		if (ControllerUser.createUser(login, sha256hex, firstname, lastname))
			return Response.status(Status.CREATED).build();
		return Response.status(Status.CONFLICT).build();

	}

	@POST
	@SigninNeeded
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setUser(@Context SecurityContext security, User user) {
		String email = security.getUserPrincipal().getName();
		
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		
		
		if(ControllerUser.modifyUser(email, user)){
			return Response.ok().build();
		}
		return Response.status(Status.CONFLICT).build();		
	}
}
