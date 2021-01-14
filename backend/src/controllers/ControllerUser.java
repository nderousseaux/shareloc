package controllers;

import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

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
		try {
			UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			transaction.begin();
			
			daoUser.getEntityManager().joinTransaction();
			
			Query query = daoUser.getEntityManager().createQuery("UPDATE User u SET u.lastname = :lastname, u.firstname = :firstname, u.password = :password WHERE u.email = :email");
			query.setParameter("lastname", user.getLastname()).setParameter("firstname", user.getFirstname()).setParameter("password", user.getPassword()).setParameter("email", mail).executeUpdate();
			transaction.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	
	

}
