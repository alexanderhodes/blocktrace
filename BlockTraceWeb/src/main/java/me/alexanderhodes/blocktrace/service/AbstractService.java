package me.alexanderhodes.blocktrace.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Created by alexa on 23.09.2017.
 */
public abstract class AbstractService<T> {

	@PersistenceContext(unitName = "blocktracePU")
	protected EntityManager entityManager;

	private Class<T> entityClass;

	public AbstractService() {

	}

	public AbstractService(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Saving entity in database
	 * 
	 * @param entity Entity which has to be stored in database
	 * @return stored Entity
	 */
	public T persist(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Query entity by id
	 * 
	 * @param id id of entity
	 * @return found entity
	 */
	public T findById(Long id) {
		try {
			return entityManager.find(entityClass, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Remove object from database
	 * 
	 * @param entity Object which has to be removed from database
	 */
	public void remove(T entity) {
		entityManager.remove(entity);
	}

	/**
	 * Updating object in database
	 * 
	 * @param entity Object which has to be updated
	 * @return updated object
	 */
	public T merge(T entity) {
		entityManager.merge(entity);
		return entity;
	}

	/**
	 * Query list of object with a specific start and end position
	 * 
	 * @param startPosition number of first object received
	 * @param maxResult number of last object received
	 * @return list of objects
	 */
	public List<T> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<T> findAllQuery = getListAllQuery();
		// set start position
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		// set end position
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<T> results = findAllQuery.getResultList();
		return results;
	}

	/**
	 * Query all objects of one entity in database
	 * 
	 * @return List of all objects
	 */
	public List<T> listAll() {
		return getListAllQuery().getResultList();
	}

	/**
	 * Create TypedQuery for one entity
	 * 
	 * @return TypedQuery for entity
	 */
	public TypedQuery<T> getListAllQuery() {
		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
		return entityManager.createQuery(criteria.select(criteria.from(entityClass)));
	}

}
