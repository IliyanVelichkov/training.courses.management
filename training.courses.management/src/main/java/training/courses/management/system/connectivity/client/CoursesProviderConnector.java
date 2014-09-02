package training.courses.management.system.connectivity.client;

import org.apache.http.Header;

import training.courses.management.system.connectivity.HttpConnector;
import training.courses.management.system.connectivity.exception.ServiceException;

public class CoursesProviderConnector extends HttpConnector {

	public CoursesProviderConnector(String url) {
		super(url);
	}

	@Override
	protected Header createAuthHeader() throws ServiceException {
		return null;
	}

}
