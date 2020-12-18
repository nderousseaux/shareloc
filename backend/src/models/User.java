package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="User")
public class User {
	@Id
	private String email;
	private String password;
	private String lastname;
	private String firstname;

	public User() {	
	}
	
	public User(String login, String password, String lastname, String firstname) {
		super();
		this.email = login;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public String getLogin() {
		return email;
	}

	public void setLogin(String login) {
		this.email = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}
