package co.simplon.poleEmploi.crud.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

@Entity
@Table(name = "stays")
@NamedQueries({ 
	@NamedQuery(name = "Stay.findAll", query = "SELECT s FROM Stay s")
})
public class Stay implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "cities_pk", referencedColumnName = "id")
	 private City city;

	 @ManyToOne
	 @JoinColumn(name = "persons_pk", referencedColumnName = "id")
	 private Person person;

	 @Column(name = "arrival")
	 private ZonedDateTime arrival;

	 @Column(name = "departure")
	 private ZonedDateTime departure;

	 public Stay(){
		 
	 }
	 public Stay(City city, Person person, ZonedDateTime arrival, ZonedDateTime departure){
		 this.city= city;
		 this.person= person;
		 this.arrival= arrival;
		 this.departure= departure;
	 }
	 public Person getPerson() {
		 return person;
	 }
	 public void setPerson(Person person) {
		 this.person.getStays().remove(this);
		 person.getStays().add(this);
		 this.person= person;
	 }
	 public City getCity() {
		 return city;
	 }
	 public void setCity(City city) {
		 this.city.getStays().remove(this);
		 city.getStays().add(this);
		 this.city= city;
	 }
	 public ZonedDateTime getArrival() {
		 return arrival;
	 }
	 public void setArrival(ZonedDateTime arrival) {
		 this.arrival= arrival;
	 }
	
	 public ZonedDateTime getDeparture() {
		 return departure;
	 }
	 public void setDeparture(ZonedDateTime departure) {
		 this.departure= departure;
	 }
	 
	 public static Stay createStay(City city, Person person, ZonedDateTime arrival, ZonedDateTime departure) {
		 Stay res= new Stay(city, person, arrival, departure);
		 city.getStays().add(res);
		 person.getStays().add(res);
		 return res;
	 }
	 public static Stay createStay(City city, Person person, String arrival, String departure) {
		 return createStay(city, person, ZonedDateTime.parse(arrival), ZonedDateTime.parse(departure));
	 }
		 public static void deleteStay(Stay s) {
		 s.person.getStays().remove(s);
		 s.city.getStays().remove(s);
	 }
	 @Override
	 public String toString() {
		 return "{ city :"+city+"\n"+
				 "person :"+ person+"\n"+
				 "arrival :"+ arrival+"\n"+
				 "departure :"+departure+"\n"+
				 "id : "+ id+" }";
	 }
}