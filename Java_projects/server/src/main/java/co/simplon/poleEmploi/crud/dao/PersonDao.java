package co.simplon.poleEmploi.crud.dao;

import javax.persistence.*;
import co.simplon.poleEmploi.crud.domain.*;

public class PersonDao extends AbstractDao<Person> {
	public PersonDao(EntityManager em) {
		super(Person.class, "Person", em);
	}
}
