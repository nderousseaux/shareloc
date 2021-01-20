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
	@JoinColumn(nullable = false, name = "idService")
	private Service service;
	
	
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "emailBeneficiaire")
	private User userB;
	
	public Beneficiaire() {
	}
	
	
	public Beneficiaire(Service s, User ub) {
		super();
		
		this.userB = ub;
		this.service = s;

	}
	
	public int getId() {
		return id;
	}
	
	public Service getService() {
		return service;
	}

	public void setService(Service s) {
		service = s;
	}
	
	public User getBeneficiaire() {
		return userB;
	}

	public void setBeneficiaire(User u) {
		userB = u;
	}


}
