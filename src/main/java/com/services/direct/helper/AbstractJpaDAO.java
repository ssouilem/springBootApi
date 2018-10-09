package com.services.direct.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractJpaDAO<K, T> {

    /**
     * The entity manager.
     */
    @PersistenceContext
	protected EntityManager entityManager;

	/** The clazz. */
	private Class<T> clazz;

    /**
     * Instantiates a new Abstract jpa dao.
     */
    @SuppressWarnings("unchecked")
	public AbstractJpaDAO() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

    /**
     * Persist.
     *
     * @param entity the entity
     */
    public void persist(T entity) {
		entityManager.persist(entity);
	}

    /**
     * Persist and flush an entity before returning it with the generated id set.
     *
     * @param entity the entity
     * @return t
     */
    public T persistAndFlush(T entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

    /**
     * Removes the.
     *
     * @param entity the entity
     */
    public void remove(T entity) {
		entityManager.remove(entity);
	}

    /**
     * Removes the by id.
     *
     * @param id the id
     */
    public void removeById(K id) {
		final T entity = findById(id);
		remove(entity);
	}

    /**
     * Merge.
     *
     * @param entity the entity
     * @return the managed instance that the state was merged to
     */
    public T merge(T entity) {
		return entityManager.merge(entity);
	}

    /**
     * Refresh.
     *
     * @param entity the entity
     */
    public void refresh(T entity) {
		entityManager.refresh(entity);
	}

    /**
     * Find by id.
     *
     * @param id the id
     * @return the t
     */
    public T findById(K id) {
		return entityManager.find(clazz, id);
	}

    /**
     * Load.
     *
     * @param id the id
     * @return the t
     */
    public T load(K id) {
		return entityManager.getReference(clazz, id);
	}

    /**
     * Flush.
     *
     * @param entity the entity
     * @return the t
     */
    public T flush(T entity) {
		entityManager.flush();
		return entity;
	}

    /**
     * Find all.
     *
     * @return the list
     */
    @SuppressWarnings("unchecked")
	public List<T> findAll() {

		return entityManager.createQuery("from " + getClazz().getName() + " h").getResultList();
	}

    /**
     * Removes the all.
     *
     * @return the integer
     */
    public Integer removeAll() {
		return entityManager.createQuery("DELETE FROM " + getClazz().getName() + " h").executeUpdate();
	}

    /**
     * Gets clazz.
     *
     * @return the clazz
     */
    public Class<T> getClazz() {
		return clazz;
	}


}