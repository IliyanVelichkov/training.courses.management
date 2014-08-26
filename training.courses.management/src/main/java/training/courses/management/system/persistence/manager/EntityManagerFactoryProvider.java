package training.courses.management.system.persistence.manager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public enum EntityManagerFactoryProvider {

	INSTANCE;

	private static final String PERSISTENCE_UNIT_NAME = "trainings-management"; //$NON-NLS-1$

	private EntityManagerFactory factory = null;

	public synchronized EntityManagerFactory getEntityManagerFactory() {
		if (this.factory == null) {
			this.factory = createEntityManagerFactory(DataSourceProvider.INSTANCE.get());
		}
		return this.factory;
	}

	private EntityManagerFactory createEntityManagerFactory(DataSource dataSource) {
		final Map<Object, Object> properties = createProperties(dataSource);
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	private Map<Object, Object> createProperties(DataSource dataSource) {
		final Map<Object, Object> properties = new HashMap<>();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);
		return properties;
	}

	public synchronized void close() {
		if (factory != null && factory.isOpen()) {
			factory.close();
			factory = null;
		}
	}

}
