package models;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;





@Entity
@Table(name="VoteAdd")
public class VoteAdd implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name = "idTask")
	private Task task;

	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "email")
	private User user;

	@Column(name ="vote")
	private boolean vote;
	public VoteAdd() {	
	}
	
	public VoteAdd(Task task, User user, boolean vote) {
		super();
		this.task = task;
		this.user = user;
		this.vote = vote;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}	

}
