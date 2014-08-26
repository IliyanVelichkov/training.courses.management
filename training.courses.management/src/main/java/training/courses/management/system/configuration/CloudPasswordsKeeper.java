package training.courses.management.system.configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sap.cloud.security.password.PasswordStorage;

public class CloudPasswordsKeeper implements PasswordsKeeper {

	private static final String PASS_STORAGE_RES_NAME = "java:comp/env/PasswordStorage"; //$NON-NLS-1$
	private static CloudPasswordsKeeper instance;

	private PasswordStorage passwordStorage;

	public static CloudPasswordsKeeper getInstance() throws NamingException {
		if (null == instance) {
			instance = new CloudPasswordsKeeper();
		}
		return instance;
	}

	private CloudPasswordsKeeper() throws NamingException {
		this.passwordStorage = getPasswordStorage();
	}

	private PasswordStorage getPasswordStorage() throws NamingException {
		InitialContext ctx = new InitialContext();
		return (PasswordStorage) ctx.lookup(PASS_STORAGE_RES_NAME);
	}

	@Override
	public String getPassword(String passAlias) {
		return new String(passwordStorage.getPassword(passAlias));
	}
}
