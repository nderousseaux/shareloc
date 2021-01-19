package models;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;



@Entity
@Table(name="Beneficiaire")
public class Beneficiaire implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="id")
    private Integer id;
	
	
	@ManyToOne 
	@JoinColumn(nullable = false, name = "idTask")
	private Task task;

	
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "email")
	private User user;
	
	
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "emailBeneficiaire")
	private User userB;
	
	public Beneficiaire() {
	}
	
	
	public Beneficiaire(Task t, User u, User ub) {
		super();
		this.user = u;
		this.userB = ub;
		this.task = t;

	}
	
	public int getId() {
		return id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		user = u;
	}
	
	public User getBeneficiaire() {
		return userB;
	}

	public void setBeneficiaire(User u) {
		userB = u;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}


}
