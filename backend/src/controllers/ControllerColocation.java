package controllers;

import java.util.List;

import dao.UserColocationDao;
import dao.ColocationDao;
import models.Colocation;
import models.User;
import models.UserColocation;

public class ControllerColocation {
	static UserColocationDao daoUserColoc = new UserColocationDao();
	static ColocationDao daoColoc = new ColocationDao();

	
	public static List<Colocation> getColocations(String username) {
		return daoUserColoc.getEntityManager().createNamedQuery("coloc_user.getColocsByUser").setParameter("user", username).getResultList();
	}
	
	public static List<User> getUserFromColoc(int idColoc) {
		return daoUserColoc.getEntityManager().createNamedQuery("coloc_user.getUserByColoc").setParameter("colocation", idColoc).getResultList();
	}
	
	public static Colocation getColocation(int idColoc) {
		return daoColoc.find(idColoc);
	}
	
	public static boolean create(User user, Colocation coloc) {
		try {
			//On crée la coloc
			daoColoc.create(coloc);
			
			//Celui qui l'a crée devient automatiquement membre et manager de celle çi.
			daoUserColoc.create(new UserColocation(coloc, user, true));
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
}
