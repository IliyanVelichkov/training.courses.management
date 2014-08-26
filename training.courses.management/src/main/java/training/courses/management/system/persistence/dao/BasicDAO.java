package training.courses.management.system.persistence.dao;

import static training.courses.management.system.persistence.dao.QueryPattern.GET_ALL_ENTITIES;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.persistence.manager.EntityManagerProvider;

public class BasicDAO<T> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected EntityManagerProvider emProvider;

	public BasicDAO(EntityManagerProvider emProvider) {
		this.emProvider = emProvider;
	}

	public void refresh(T object) {
		final EntityManager em = emProvider.get();
		em.refresh(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		final List<T> result = new ArrayList<>();
		final EntityManager em = emProvider.get();
		String query = String.format(GET_ALL_ENTITIES.getPattern(), getTableName());
		result.addAll((Collection<? extends T>) em.createQuery(query, this.getClass().getGenericSuperclass().getClass()).getResultList());
		return result;
	}

	public T save(T entity) {
		final EntityManager em = emProvider.get();
		em.getTransaction().begin();

		final T merge = em.merge(entity);

		em.getTransaction().commit();
		return merge;
	}

	public T saveNew(T entity) {
		final EntityManager em = emProvider.get();
		em.getTransaction().begin();
		em.persist(entity);

		em.getTransaction().commit();
		return entity;
	}

	public T getById(long id) {
		final EntityManager em = emProvider.get();
		return getById(id, em);
	}

	@SuppressWarnings("unchecked")
	private T getById(long id, EntityManager em) {
		T t = null;

		try {
			Query query = em.createQuery("select u from " + getTableName() + " u where u.id = :id"); //$NON-NLS-1$ //$NON-NLS-2$
			query.setParameter("id", id); //$NON-NLS-1$
			t = (T) query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Could not retrieve entity {} from table {}.", id, getTableName()); //$NON-NLS-1$
		} catch (NonUniqueResultException e) {
			logger.error("More than one entity {} from table {}.", id, getTableName()); //$NON-NLS-1$
		}

		return t;
	}

	private Type getActualType() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) genericSuperclass;
		Type type = pt.getActualTypeArguments()[0];

		return type;
	}

	private String getTableName() {
		String actualType = getActualType().toString();
		return actualType.substring(actualType.lastIndexOf('.') + 1);

	}

	public T find(Class<T> clazz, Object primaryKey) {
		final EntityManager em = emProvider.get();
		return em.find(clazz, primaryKey);
	}

	public void delete(T entity) {
		final EntityManager em = emProvider.get();

		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
	}

}
