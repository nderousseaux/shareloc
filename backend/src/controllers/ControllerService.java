package controllers;

import java.util.Date;
import java.util.List;

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
		daoService.create(new Service(t, u, false, new Date()));
		
		
		//On ajoute chacun des bénificiaires
		for(User b : bs) {
			daoBenef.create(new Beneficiaire(t, u, b));
		}
	}
	
}
