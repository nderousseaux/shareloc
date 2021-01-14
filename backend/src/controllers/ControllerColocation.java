package controllers;

import java.util.List;

import dao.UserColocationDao;
import models.Colocation;

public class ControllerColocation {
	static UserColocationDao daoUserColoc = new UserColocationDao();

	public static List<Colocation> getColocations(String username) {
		return daoUserColoc.getEntityManager().createNamedQuery("coloc_user.getColocsByUser").setParameter("user", username).getResultList();
	}
}
