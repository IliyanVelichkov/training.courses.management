package training.courses.management.system.persistence.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public enum EntityManagerProvider {

	INSTANCE;

	private static final ThreadLocal<EntityManager> ENTITY_MANAGERS = new ThreadLocal<>();

	public void initEntityManagerProvider() {
		EntityManagerFactory emf = EntityManagerFactoryProvider.INSTANCE.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		set(em);
	}

	public EntityManager get() {
		return ENTITY_MANAGERS.get();
	}

	public void closeEntityManager() {
		final EntityManager em = get();
		if (em != null) {
			em.close();
		}
		remove();
	}

	private static void set(EntityManager entityManager) {
		ENTITY_MANAGERS.set(entityManager);
	}

	private static void remove() {
		ENTITY_MANAGERS.remove();
	}

}
