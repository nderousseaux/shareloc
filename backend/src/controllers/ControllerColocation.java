package controllers;

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
	
	public static boolean modifyColoc(int colocId, Colocation coloc) {
		try {
			UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			transaction.begin();
			
			daoColoc.getEntityManager().joinTransaction();
			
			Query query = daoColoc.getEntityManager().createQuery("UPDATE Colocation c SET c.name = :name WHERE c.idColocation = :colocId");
			query.setParameter("name", coloc.getName()).setParameter("colocId", colocId).executeUpdate();
			transaction.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public static boolean isInColocation(User u, Colocation c) {
		//Get user from colocation
		List<User> users = ControllerColocation.getUserFromColoc(c.getId());
				
		//On vérifie que l'user est dans la coloc
		Boolean isIn = false;
		for(User user : users) {
			if (user.getEmail().equals(u.getEmail()))
				isIn = true;
		}
		
		return isIn;
			
	}
	
	public static boolean isManagerInColocation(User u, Colocation c) {
		List<UserColocation> ucs = daoUserColoc.findAll();
		
		//On vérifie que l'user est manager dans la coloc
		Boolean isIn = false;
		for(UserColocation uc : ucs) {
			if (uc.getUser().getEmail().equals(u.getEmail()) && uc.getIsManager() && uc.getColoc().getId() == c.getId())
				isIn = true;
		}
		
		return isIn;
		
	}
	
}
