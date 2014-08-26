package training.courses.management.system.configuration;

public class SystemEnvironmentPasswordKeeper implements PasswordsKeeper {

	private static SystemEnvironmentPasswordKeeper instance;

	public static SystemEnvironmentPasswordKeeper getIntance() {
		if (null == instance) {
			instance = new SystemEnvironmentPasswordKeeper();
		}
		return instance;
	}

	@Override
	public String getPassword(String passAlias) {
		String propertyValue = System.getProperty(passAlias);
		if (null == propertyValue) {
			throw new IllegalStateException("Missing system property with alias " + passAlias); //$NON-NLS-1$
		}
		return propertyValue;
	}
}
