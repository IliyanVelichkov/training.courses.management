package training.courses.management.system.connectivity;

import javax.naming.NamingException;

import training.courses.management.system.api.commons.util.JndiUtil;

import com.sap.core.connectivity.api.DestinationException;
import com.sap.core.connectivity.api.DestinationFactory;
import com.sap.core.connectivity.api.DestinationNotFoundException;
import com.sap.core.connectivity.api.http.HttpDestination;

public enum DestinationProvider {
	INSTANCE;

	public HttpDestination lookupDestination(String name) throws DestinationException {
		try {
			DestinationFactory destFactory = JndiUtil.lookup(DestinationFactory.JNDI_NAME);
			return (HttpDestination) destFactory.getDestination(name);
		} catch (NamingException | DestinationNotFoundException ex) {
			throw new DestinationException("Error looking up for destination with name " + name, ex); //$NON-NLS-1$
		}
	}
}
