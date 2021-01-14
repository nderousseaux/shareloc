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

@NamedQueries({
	@NamedQuery(name="coloc_user.getColocsByUser", query="SELECT uc FROM UserColocation uc WHERE uc.user.email = :user"),
})


@Entity
@Table(name="UserColocation")
public class UserColocation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name = "idColocation")
	private Colocation coloc;

	@Id
	@ManyToOne 
	@JoinColumn(nullable = false, name  = "email")
	private User user;

	@Column(name ="isManager")
	private boolean isManager;
	
	public UserColocation() {
	}
	
	
	public UserColocation(Colocation coloc, User user, boolean isManager) {
		super();
		this.coloc = coloc;
		this.user = user;
		this.isManager = isManager;
	}
	
	public Colocation getColoc() {
		return coloc;
	}

	public void setColoc(Colocation coloc) {
		this.coloc = coloc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}	

}
