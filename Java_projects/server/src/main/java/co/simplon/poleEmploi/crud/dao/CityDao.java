package co.simplon.poleEmploi.crud.dao;


import javax.persistence.*;

import co.simplon.poleEmploi.crud.domain.*;

public class CityDao extends AbstractDao<City> {
	public CityDao(EntityManager em) {
		super(City.class, "City", em);
	}
	
}
