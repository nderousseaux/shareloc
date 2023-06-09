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
@Table(name="Services")
public class Service implements Serializable{
	
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

	@Column(name ="isValide")
	private Boolean isValide;
	
	@Column(name ="date")
	private Date date;
	
	public Service() {
	}
	
	
	public Service(Task t, User u, Date d) {
		super();
		this.user = u;
		this.task = t;
		this.date = d;
	}
	
	public Integer getId() {
		return id;
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

	public Boolean getIsValide() {
		return isValide;
	}

	public void setIsValide(Boolean isValide) {
		this.isValide = isValide;
	}	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date d) {
		this.date = d;
	}

}
