package co.simplon.poleEmploi.crud.dao;

import javax.persistence.*;
import co.simplon.poleEmploi.crud.domain.*;;

public class StayDao extends AbstractDao<Stay> {

	public StayDao(EntityManager em) {
		super(Stay.class, "Stay", em);
	}
	public Stay updatePerson(Stay s, Person p) {
		em.getTransaction().begin();
		Person oldPerson= s.getPerson();
		try {
			s.setPerson(p);
			em.merge(oldPerson);
			em.merge(p);
			em.merge(s);
			em.getTransaction().commit();
		}catch(RollbackException e) {
			s.setPerson(oldPerson);
			System.err.println("failed to update stay :"+ s +"\n with person :"+p+
					"\n Rolling back !");
		}
		return s;
	}
	public Stay updateCity(Stay s, City c) {
		em.getTransaction().begin();
		City oldCity= s.getCity();
		try {
			s.setCity(c);
			em.merge(oldCity);
			em.merge(c);
			em.merge(s);
			em.getTransaction().commit();
		}catch(RollbackException e) {
			s.setCity(oldCity);
			System.err.println("failed to update stay :"+ s +"\n with city :"+c+
					"\n Rolling back !");
		}
		return s;
	}
	public Stay updatePersonAndCity(Stay s, Person p, City c) {
		em.getTransaction().begin();
		Person oldPerson= s.getPerson();
		City oldCity= s.getCity();
		try {
			s.setPerson(p);
			s.setCity(c);
			em.merge(oldPerson);
			em.merge(p);
			em.merge(oldCity);
			em.merge(c);
			em.merge(s);
			em.getTransaction().commit();
		}catch(RollbackException e) {
			s.setPerson(oldPerson);
			s.setCity(oldCity);
			System.err.println("failed to update stay :"+ s 
					+"\n with person :"+p
					+"\n with city :"+c+
					"\n Rolling back !");
			throw e;
		}
		return s;
	}
}
