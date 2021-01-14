package controllers;

import java.util.List;
import javax.persistence.Query;

import dao.UserDao;
import models.User;

public class ControllerUser {
	static UserDao daoUser = new UserDao();

	public static List<User> getUsers() {
		List<User> lv = daoUser.findAll();
		return lv;
	}

	public static User getUser(String login) {
		if (login == null)
			return null;

		User u = getUserByLogin(login);
		return u;
	}
	
	public static User getUserByLogin(String email) {
		try {
			User u = (User) daoUser.getEntityManager().createNamedQuery("User.getUserByLogin").setParameter("email", email).getResultList().get(0);
			return u;
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static User login(String login, String password) {
		User u = getUserByLogin(login);
		if (u != null && u.getPassword().equals(password))
			return u;
		return null;
	}

	public static boolean createUser(String login, String password, String firstname, String lastname) {
		User u = daoUser.find(login);
		if (u == null) {
			daoUser.create(new User(login, password, firstname, lastname));
			return true;
		}
		return false;
	}
	
	public static boolean modifyUser(String mail, User user) {
		return daoUser.updateUser(mail, user);

	}
	
	
	

}
