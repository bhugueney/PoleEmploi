package co.simplon.poleEmploi.crud.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "cities")
@NamedQueries({ 
//	@NamedQuery(name = "City.find", query = "SELECT c FROM City c WHERE c.id= :id"),
	@NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
})
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "longitude", nullable = false)
	private double longitude;
	
	@Column(name = "latitude", nullable = false)
	private double latitude;

	@OneToMany(mappedBy="city",fetch = FetchType.LAZY)
	private List<Stay> stays;

	
	public City() {
		
	}
	public City(String name, double longitude, double latitude){
		this.name= name;
		this.longitude= longitude;
		this.latitude= latitude;
		this.stays= new ArrayList<Stay>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id= id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name= name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(final double longitude) {
		this.longitude= longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(final double latitude) {
		this.latitude= latitude;
	}
	public List<Stay> getStays(){
		return stays;
	}
	public void setStays(List<Stay> stays) {
		this.stays= stays;
	}
	@Override
	public String toString() {
		return "{ name : \""+name+"\"\n"+
				" longitude : "+longitude+"\n"+
				" latitude : "+latitude+"\n"+
				"nb stays:"+stays.size()+"\n"+
				"id : "+id+"}";
	}
}