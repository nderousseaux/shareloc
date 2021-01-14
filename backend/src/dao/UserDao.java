package dao;

import models.User;

import javax.naming.InitialContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.persistence.Query;


public class UserDao extends DAOFacade<User> {

	public UserDao() {
		super(User.class);
	}
	
	@Transactional
	public boolean updateUser(String mail, User user) {
		try {
			UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			transaction.begin();
			
			getEntityManager().joinTransaction();
			
			Query query = getEntityManager().createQuery("UPDATE User u SET u.lastname = :lastname, u.firstname = :firstname, u.password = :password WHERE u.email = :email");
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
