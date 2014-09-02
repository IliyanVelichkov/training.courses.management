package training.courses.management.system.connectivity.lms.client;

import training.courses.management.system.commons.util.GsonFactory;
import training.courses.management.system.connectivity.Connector;
import training.courses.management.system.connectivity.ConnectorFactory;
import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.lms.client.beans.CatalogSearchRequest;

import com.google.gson.Gson;

public class LMSClient {

	private static final String CATALOG_SEARCH_PATH = "/learning/public-api/rest/v1/current-user/catalogs/~search"; //$NON-NLS-1$

	private Connector connector;
	private Gson gson;

	public LMSClient() {
		this.connector = ConnectorFactory.INSTANCE.createLMSConnector();
		this.gson = GsonFactory.INSTANCE.getGson();
	}

	public String searchTrainingCourses(String searchPhrase) throws ServiceException {
		String searchJSON = gson.toJson(new CatalogSearchRequest(searchPhrase));
		ServiceHttpResponse resp = connector.postJSON(CATALOG_SEARCH_PATH, searchJSON);
		return resp.getBody();
	}
}
