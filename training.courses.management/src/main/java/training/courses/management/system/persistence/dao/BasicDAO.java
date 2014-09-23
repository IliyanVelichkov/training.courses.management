package training.courses.management.system.persistence.dao;

import static training.courses.management.system.persistence.dao.QueryPattern.GET_ALL_ENTITIES;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import training.courses.management.system.persistence.manager.EntityManagerProvider;

public class BasicDAO<T> {

	private EntityManagerProvider emProvider;

	public BasicDAO(EntityManagerProvider emProvider) {
		this.emProvider = emProvider;
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
