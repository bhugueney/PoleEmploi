package co.simplon.poleEmploi.crud.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


public abstract class AbstractDao <T extends Serializable> implements IDao<T> {
	protected Class<T> clazz;
   
    protected EntityManager em;
    protected String queriesPrefix;
    AbstractDao(final Class<T> clazz, String queriesPrefix, EntityManager em){
    	this.clazz= clazz;
    	this.em= em;
    	this.queriesPrefix= queriesPrefix;
    }
    // API

    public T findOne(final long id) {
        return em.find(clazz, Long.valueOf(id));
    }

	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return (List<T>)(em.createNamedQuery(queriesPrefix+".findAll").getResultList());
	}
	@SuppressWarnings("unchecked")
	public List<T> findAllPaged(final int pageNum, final int pageSize ){
		return (List<T>)(em.createNamedQuery(queriesPrefix+".findAll")
				.setFirstResult(pageNum*pageSize)
				.setMaxResults(pageSize).getResultList());
	}

    public T create(final T entity) {
    	em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public T update(final T entity) {
    	em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        return entity;
    }

    public void delete(final T entity) {
    	em.getTransaction().begin();
    	em.remove(entity);
    	em.getTransaction().commit();
    }

    public void deleteById(final long entityId) {
        delete(findOne(entityId));
    }

    
    
}
