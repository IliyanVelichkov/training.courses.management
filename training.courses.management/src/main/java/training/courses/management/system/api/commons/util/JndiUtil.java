package training.courses.management.system.api.commons.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JndiUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(JndiUtil.class);

	private JndiUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name) throws NamingException {
		LOGGER.debug("Looking up {}", name); //$NON-NLS-1$
		Context ctx = new InitialContext();
		return (T) ctx.lookup(name);
	}
}
