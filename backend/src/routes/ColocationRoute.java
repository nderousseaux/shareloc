package routes;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.PathParam;

import org.jose4j.json.internal.json_simple.JSONObject;

import controllers.ControllerColocation;
import models.Colocation;
import security.SigninNeeded;

@Path("/colocation")
public class ColocationRoute {

	@GET
	@SigninNeeded
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response colocations() {
		List<Colocation> lc = ControllerColocation.getColocations();
				
		return Response.ok().entity(lc).build();
	}
	
	@SigninNeeded
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColloc(@PathParam("name") String name) {
		Colocation c = ControllerColocation.getColocation(name);
		if (c != null) {
			return Response.ok().entity(c).build();
		}
		else return null;
	}
	
	@POST
	@SigninNeeded
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCollocation(JSONObject params) {
		if(ControllerColocation.createColocation(params.get("name").toString()))
			return Response.status(Status.CREATED).build();
		return Response.status(Status.CONFLICT).build();
	}
	
	/*
	
	@SigninNeeded
	@PUT
	@Path("/updateColloc/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCollocation(@PathParam("id") int id, JSONObject params) {
		if(CollocationManager.modifyCollocation(id ,params.get("name").toString()))
			return Response.status(Status.ACCEPTED).build();
		return Response.status(Status.CONFLICT).build();
	}
	
	@SigninNeeded
	@GET
	@Path("/getColloc/{idColloc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCollocForUser(@PathParam("idColloc") int idColloc) {
		Collocation c = CollocationManager.getColloc(idColloc);
		if (c != null) {
			return Response.ok().entity(c).build();
		}
		else return null;
	}
	
	@SigninNeeded
	@DELETE
	@Path("/deleteColloc/{idColloc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCollocation(@PathParam("idColloc") int idColloc) {
		try {
			CollocationManager.deleteCollocation(idColloc);
			return Response.status(Status.ACCEPTED).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}	*/

	
	

}
