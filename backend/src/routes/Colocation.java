package routes;

import java.util.ArrayList;
import java.util.List;

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
import models.User;
import security.JWTokenUtility;
import security.SigninNeeded;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.core.SecurityContext;

@Path("/colocation")
public class Colocation {

	@GET
	@SigninNeeded
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColocations(@Context SecurityContext security) {
		
		List<models.Colocation> colocs = ControllerColocation.getColocations(security.getUserPrincipal().getName());
		
		if (colocs.size() != 0)
			return Response.ok().entity(colocs).build();
		return Response.status(Status.NO_CONTENT).build();
	}
}
