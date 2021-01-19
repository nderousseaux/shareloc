package models;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="task.getTasksByColoc", query="SELECT t FROM Task t WHERE t.colocation.idColocation = :coloc"),
})

@Entity
@Table(name="Task")
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="idTask")
	private int idTask;

	@Column(name ="name")
	private String name;

	@Column(name ="description")
	private String description;

	@Column(name ="cost")
	private int cost;
	
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "idColocation")
	private Colocation colocation;

	
	public Task() {
	}
	
	public Task(String name, String description, int cost, Colocation colocation) {
		super();
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.colocation = colocation;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public Colocation getColoc() {
		return colocation;
	}

	public void setColoc(Colocation coloc) {
		this.colocation = coloc;
	}

}
