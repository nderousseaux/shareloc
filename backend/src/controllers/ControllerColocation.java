package controllers;

import java.util.List;

import dao.ColocationDao;
import models.Colocation;

public class ControllerColocation {
	static ColocationDao daoColocation = new ColocationDao();

	public static List<Colocation> getColocations() {
		List<Colocation> lv = daoColocation.findAll();
		return lv;
	}

	public static Colocation getColocation(String name) {
		Colocation u = daoColocation.find(name);
		return u;
	}

	public static boolean createColocation(String name) {
		Colocation u = daoColocation.find(name);
		if (u == null) {
			daoColocation.create(new Colocation(name));
			return true;
		}
		return false;
	}

}
