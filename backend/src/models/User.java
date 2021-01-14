package models;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;



@NamedQueries({
	@NamedQuery(name="User.getUserByLogin", query="SELECT u FROM User u WHERE u.email = :email"),
})


@Entity
@Table(name="User")

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name ="email")
	private String email;
	@Column(name ="password")
	private String password;
	@Column(name ="lastname")
	private String lastname;
	@Column(name ="firstname")
	private String firstname;

	public User() {	
	}
	
	public User(String email, String password, String lastname, String firstname) {
		super();
		this.email = email;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
