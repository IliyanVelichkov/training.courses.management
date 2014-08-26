package training.courses.management.system.persistence.manager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DataSourceProvider {

	INSTANCE;

	private static final String DB_RES_NAME = "java:comp/env/jdbc/DefaultDB"; //$NON-NLS-1$

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DataSource dataSource;

	private DataSourceProvider() {
		try {
			final InitialContext ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup(DB_RES_NAME);
		} catch (NamingException e) {
			logger.error("Could not lookup the default datasource", e); //$NON-NLS-1$
			throw new RuntimeException(e);
		}
	}

	public DataSource get() {
		return dataSource;
	}

}
