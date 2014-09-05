package training.courses.management.system.configuration;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PasswordsKeeperFactory {
	INSTANCE;
	private static final String HC_HOST_ENV_PROPERTY = "HC_HOST"; //$NON-NLS-1$

	private Logger logger = LoggerFactory.getLogger(PasswordsKeeperFactory.class);
	private PasswordsKeeper passwordsKeeper;

	private PasswordsKeeperFactory() {
		this.passwordsKeeper = createaAppropriatePassKeeper();
	}

	private PasswordsKeeper createaAppropriatePassKeeper() {
		boolean isCloudScenario = null != System.getenv(HC_HOST_ENV_PROPERTY);
		if (isCloudScenario) {
			try {
				PasswordsKeeper passKeeper = CloudPasswordsKeeper.getInstance();
				logger.info("Will be used cloud password keeper."); //$NON-NLS-1$
				return passKeeper;
			} catch (NamingException ex) {
				logger.info("Missing cloud password keeper. Will be used environment password provider instead.", ex); //$NON-NLS-1$
				return SystemEnvironmentPasswordKeeper.getIntance();
			}

		} else {
			PasswordsKeeper passKeeper = SystemEnvironmentPasswordKeeper.getIntance();
			logger.info("Will be used system environment password keeper."); //$NON-NLS-1$
			return passKeeper;
		}
	}

	public PasswordsKeeper getPasswordsKeeper() {
		return this.passwordsKeeper;
	}

}
