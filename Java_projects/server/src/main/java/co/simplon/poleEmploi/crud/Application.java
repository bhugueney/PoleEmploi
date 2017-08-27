package co.simplon.poleEmploi.crud;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import co.simplon.poleEmploi.crud.dao.*;
import co.simplon.poleEmploi.crud.domain.*;


public class Application {
	
	private PersonDao personDao;
	private CityDao cityDao;
	private StayDao stayDao;
	
	MappedCommands commands;
	public Application(EntityManager em) {
		this.personDao= new PersonDao(em);
		this.cityDao= new CityDao(em);
		this.stayDao= new StayDao(em);
		commands= new MappedCommands();
		MappedCommands createCommands= buildCreateCommands(new MappedCommands());
		MappedCommands readCommands= buildReadCommands(new MappedCommands());
		MappedCommands updateCommands= buildUpdateCommands(new MappedCommands());
		MappedCommands deleteCommands= buildDeleteCommands(new MappedCommands());
		commands.add("CREATE", createCommands).add("READ", readCommands).add("UPDATE", updateCommands).add("DELETE", deleteCommands);	
	}
	
	private MappedCommands buildCreateCommands(MappedCommands cmds) {
		cmds.add("CITY", new ICommand() {
		public String process(Scanner sc) {
			String name= sc.next();
			double longitude= sc.nextDouble();	
			double latitude= sc.nextDouble();	
			City c=cityDao.create(new City(name, longitude, latitude));
			return "created City:"+c;
		}});
		cmds.add("PERSON", new ICommand() {
		public String process(Scanner sc) {
			String firstname= sc.next();
			String lastname= sc.next();	
			Person p=personDao.create(new Person(firstname, lastname));
			return "created Person:"+p;
		}});
		cmds.add("STAY", new ICommand() {
		public String process(Scanner sc) {
			City city= cityDao.findOne(sc.nextLong());
			Person person= personDao.findOne(sc.nextLong());
			String arrival= sc.next();
			String departure= sc.next();
			Stay s= stayDao.create(Stay.createStay(city, person, arrival, departure));
			return "created Stay:"+s;
		}});
		return cmds;
	}
	private static<T> String collectionToString(Collection<T> c, String sep) {
		String res="";
		for(T elt:c) {
			res+=elt+sep;
		}
		return res;
	}
	private static<T> String collectionToString(Collection<T> c) {
		return collectionToString(c, "\n");
	}
	private MappedCommands buildReadCommands(MappedCommands cmds) {
		cmds.add("CITY", new ICommand() {
		public String process(Scanner sc) {
			return collectionToString(cityDao.findAllPaged(1, 16));
		}});
		cmds.add("PERSON", new ICommand() {
		public String process(Scanner sc) {
			return collectionToString(personDao.findAll());
		}});
		cmds.add("STAY", new ICommand() {
		public String process(Scanner sc) {
			return collectionToString(stayDao.findAll());
		}});
		return cmds;
	}
	private MappedCommands buildUpdateCommands(MappedCommands cmds) {
		cmds.add("CITY", new ICommand() {
		public String process(Scanner sc) {
			City c= cityDao.findOne(sc.nextLong());
			c.setName(sc.next());
			c.setLongitude(sc.nextDouble());
			c.setLatitude(sc.nextDouble());
			cityDao.update(c);
			return "updated City:"+c;
		}});
		cmds.add("PERSON", new ICommand() {
		public String process(Scanner sc) {
			Person p= personDao.findOne(sc.nextLong());
			p.setFirstname(sc.next());
			p.setLastname(sc.next());
			personDao.update(p);
			return "updated Person:"+p;
		}});
		cmds.add("STAY", new ICommand() {
		public String process(Scanner sc) {
			Stay s= stayDao.findOne(sc.nextLong());
			Person p= personDao.findOne(sc.nextLong());
			City c= cityDao.findOne(sc.nextLong());
			ZonedDateTime arrival= ZonedDateTime.parse(sc.next());
			ZonedDateTime departure= ZonedDateTime.parse(sc.next());
			stayDao.updatePersonAndCity(s, p, c);
			s.setArrival(arrival);
			s.setDeparture(departure);
			return "updated Stay:"+s;
		}});
		return cmds;
	}
	private MappedCommands buildDeleteCommands(MappedCommands cmds) {
		cmds.add("CITY", new ICommand() {
		public String process(Scanner sc) {
			Long id= sc.nextLong();
			cityDao.deleteById(id);
			return "deleted city with id :"+id;
		}});
		cmds.add("PERSON", new ICommand() {
		public String process(Scanner sc) {
			Long id= sc.nextLong();
			personDao.deleteById(id);
			return "deleted person with id :"+id;
		}});
		cmds.add("STAY", new ICommand() {
		public String process(Scanner sc) {
			Long id= sc.nextLong();
			stayDao.deleteById(id);
			return "deleted stay with id :"+id;
		}});
		return cmds;
	}
	public String process(Scanner sc) {
		return commands.process(sc);
	}
	public static void main(String[] args) {
		EntityManagerFactory emf= Persistence
				.createEntityManagerFactory("CrudBase");
		EntityManager em = emf.createEntityManager();		
		Scanner sc=new Scanner(System.in);
		try {
			Application crudApp= new Application(em);
			while(sc.hasNext()) {
				System.err.println("command ?");
				System.out.println(crudApp.process(sc));
			}
		/*	CityDao cDao= new CityDao(em);
			StayDao sDao= new StayDao(em);
			Person d=pDao.create(new Person("King", "Kong"));
			City c= cDao.create(new City("Monter's Island", 0,0));
			Stay s= sDao.create(Stay.createStay(c, d, ZonedDateTime.now(), ZonedDateTime.now()));
			System.err.println(d);
			System.err.println(c);
			System.err.println(s);
			d.setLastname("Update");
			d=pDao.update(d);
			Stay sf= sDao.findOne(36711,13);
			System.err.println(sf);
			*/
			//pDao.deleteById(13L);
			
		}catch(Exception e) {
			System.err.println(e);
		}finally {
			em.close();
			emf.close();
			sc.close();
		}
		System.err.println("done");
		
	}			
}
