package training.courses.management.system.connectivity;

import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.exception.ServiceException;

public interface Connector {

	ServiceHttpResponse get(String resourcePath) throws ServiceException;

	ServiceHttpResponse postJSON(String resourcePath, String jsonBody) throws ServiceException;

	ServiceHttpResponse putJSON(String resourcePath, String jsonBody) throws ServiceException;

	ServiceHttpResponse delete(String resourcePath) throws ServiceException;

	void shutDown();
}
