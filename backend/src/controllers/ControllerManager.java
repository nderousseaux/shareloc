package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UserColocationDao;
import dao.ColocationDao;
import models.Colocation;
import models.User;
import models.UserColocation;
import java.util.stream.Collectors;


public class ControllerManager {
	static UserColocationDao daoUserColoc = new UserColocationDao();
	static ColocationDao daoColoc = new ColocationDao();

	
	public static List<User> getManagers(int idColoc) {
		List<UserColocation> ucs = daoUserColoc.findAll();
		
		ucs = ucs.stream()
				.filter(item -> item.getColoc().getId() == idColoc)
				.filter(item -> item.getIsManager())
			    .collect(Collectors.toList());	
		
		List<User> u = new ArrayList<User>();
		
		for(UserColocation uc : ucs) {
			u.add(uc.getUser());
		}
		
		return u;
	}
	
	public static void upToManager(Colocation c, User u) {
		UserColocation uc = getOneUserColoc(c, u);
			
		uc.setIsManager(true);
		
		daoUserColoc.edit(uc);
		
		
	}
	
	public static void downgrade(Colocation c, User u) {		
		UserColocation uc = getOneUserColoc(c, u);
		
		uc.setIsManager(false);
		
		daoUserColoc.edit(uc);
	}
	
	public static UserColocation getOneUserColoc(Colocation c, User u) {		
		List<UserColocation> ucs = daoUserColoc.findAll();
		
		ucs = ucs.stream()
				.filter(item -> item.getColoc().getId() == c.getId())
				.filter(item -> item.getUser().getEmail() == u.getEmail())
			    .collect(Collectors.toList());	
		
		return ucs.get(0);
	}
	
	public static int nbManager(Colocation c) {			
		List<UserColocation> ucs = daoUserColoc.findAll();
			
		//
		int nbManager = 0;
		for(UserColocation uc : ucs) {
			if (uc.getIsManager() && uc.getColoc().getId() == c.getId())
				nbManager++;
		}
			
		return nbManager;
			
	}
	
	

}
