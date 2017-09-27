package me.alexanderhodes.blocktrace.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by alexa on 23.09.2017.
 */
public abstract class AbstractService<T> {

    @PersistenceContext(unitName = "blocktracePU")
    protected EntityManager entityManager;

    private Class<T> entityClass;

    public AbstractService () {

    }

    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T persist (T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T findById (Long id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remove (T entity) {
        entityManager.remove(entity);
    }

    public T merge (T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public List<T> listAll (Integer startPosition, Integer maxResult) {
        TypedQuery<T> findAllQuery = getListAllQuery();
        if (startPosition != null)
        {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null)
        {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<T> results = findAllQuery.getResultList();
        return results;
    }

    public List<T> listAll() {
        return getListAllQuery().getResultList();
    }

    public TypedQuery<T> getListAllQuery() {
        CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
        return entityManager.createQuery(criteria.select(criteria.from(entityClass)));
    }

}
