package models;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@Table(name="Colocation")
public class Colocation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="idColocation")
    private Integer idColocation;
	@Column(name ="name")
	private String name;
	
	public Colocation() {
	}
	
	
	public Colocation(String name) {
		super();
		this.name = name;
	}
	
	public Integer getId() {
		return idColocation;
	}

	public void setId(Integer id) {
		this.idColocation = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
