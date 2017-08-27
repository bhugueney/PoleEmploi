package co.simplon.poleEmploi.crud.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@NamedQueries({ 
	@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
})
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "firstname", nullable = false, length = 255)
	private String firstname;
	
	@Column(name = "lastname", nullable = false, length = 255)
	private String lastname;
	
	@OneToMany(mappedBy="person",fetch = FetchType.LAZY)
	private List<Stay> stays= new ArrayList<Stay>();
	
	public Person() {	
	}
	public Person(String firstname, String lastname) {
		this.firstname= firstname;
		this.lastname= lastname;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id= id; 
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname= firstname;
	}

	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname= lastname;
	}
	
	public List<Stay> getStays(){
		return stays;
	}
	public void setStays(List<Stay> stays) {
		this.stays= stays;
	}
	@Override
	public String toString() {
		return "{ firstname : \""+firstname+"\"\n"+
	" lastname : \""+lastname+"\"\n"+
				"nb stays:"+stays.size()+"\n"+
				"id : "+id+ "}";
	}
}
