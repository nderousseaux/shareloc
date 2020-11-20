package dao;

import models.User;

public class UserDao extends DAOFacade<User> {

	public UserDao() {
		super(User.class);
	}

}
