package controllers;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.BeneficiaireDao;
import dao.ServiceDao;
import models.Colocation;
import models.User;
import models.Service;
import models.Task;
import models.Beneficiaire;
import models.UserColocation;

public class ControllerService {
	static ServiceDao daoService = new ServiceDao();
	static BeneficiaireDao daoBenef = new BeneficiaireDao();
	
	public static void createService(Task t, User u, List<User> bs) {
		Service s = new Service(t, u, new Date());
		
		daoService.create(s);
		
		
		//On ajoute chacun des bénificiaires
		for(User b : bs) {
			daoBenef.create(new Beneficiaire(s, b));
		}
	}
	
	public static List<Service> getServiceWaiting(Colocation c) {
		
		List<Service> ss = daoService.findAll();
		
		List<Service> retour = new ArrayList<Service>();
		
		
		for(Service s: ss) {
			if(s.getTask().getColoc().getId() == c.getId() && s.getIsValide() == null) {
				retour.add(s);
			}
		}
		
		return retour;
	}
	
}
