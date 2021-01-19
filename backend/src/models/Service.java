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
@Table(name="Service")
public class Service implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name = "idTask")
	private Task task;

	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "email")
	private User user;

	@Column(name ="isValide")
	private boolean isValide;
	
	@Column(name ="date")
	private Date date;
	
	public Service() {
	}
	
	
	public Service(Task t, User u, boolean b, Date d) {
		super();
		this.user = u;
		this.task = t;
		this.date = d;
		this.isValide = b;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		user = u;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean getIsValide() {
		return isValide;
	}

	public void setIsValide(boolean isValide) {
		this.isValide = isValide;
	}	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date d) {
		this.date = d;
	}

}
