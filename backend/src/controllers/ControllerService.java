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
import dao.UserColocationDao;
import models.Colocation;
import models.User;
import models.Service;
import models.Task;
import models.Beneficiaire;
import models.UserColocation;

public class ControllerService {
	static ServiceDao daoService = new ServiceDao();
	static BeneficiaireDao daoBenef = new BeneficiaireDao();
	static UserColocationDao daoUserColoc = new UserColocationDao();

	
	public static void createService(Task t, User u, List<User> bs) {
		Service s = new Service(t, u, new Date());
		
		daoService.create(s);
		
		
		//On ajoute chacun des bénificiaires
		for(User b : bs) {
			daoBenef.create(new Beneficiaire(s, b));
		}
	}
	
	public static List<Service> getServiceWaiting(Colocation c, User u) {
		
		List<Service> ss = daoService.findAll();
		
		List<Service> retour = new ArrayList<Service>();
		
		
		for(Service s: ss) {
			if(s.getTask().getColoc().getId() == c.getId() && s.getIsValide() == null && isBeneficiaire(u, s)) {
				retour.add(s);
			}
		}
		
		return retour;
	}
	
	public static Service getService(int id) {
		return daoService.find(id);
	}
	
	public static List<Beneficiaire> getBeneficiaires(Service s){
		List <Beneficiaire> bs = daoBenef.findAll();
		
		List <Beneficiaire> retour = new ArrayList<Beneficiaire>();
		
		for(Beneficiaire b : bs) {
			if(b.getService().getId() == s.getId()) {
				retour.add(b);
			}
		}
		return retour;
	}
	
	public static boolean isBeneficiaire(User u, Service s) {
		List <Beneficiaire> bs = getBeneficiaires(s);
		
		
		for(Beneficiaire b : bs) {
			if(b.getBeneficiaire().getEmail() == u.getEmail()) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void acceptService(Service s, Boolean isValide) {
		s.setIsValide(isValide);
		daoService.edit(s);
		
		if(isValide) {
			List <Beneficiaire> bs = getBeneficiaires(s);
			User u = s.getUser();
			Colocation c = s.getTask().getColoc();
			
			//Celui qui a réalisé le service recoit ses points
			UserColocation uc = ControllerColocation.getUserColocation(u, c);
			uc.setPoints(uc.getPoints()+(s.getTask().getCost())*bs.size());
			daoUserColoc.edit(uc);
			
			//Les bénéficiaires perdent leurs points
			for (Beneficiaire b: bs) {
				UserColocation ucB = ControllerColocation.getUserColocation(b.getBeneficiaire(), c);
				ucB.setPoints(ucB.getPoints()-s.getTask().getCost());
				daoUserColoc.edit(ucB);
			}
		}
		
	}
	
	
}
