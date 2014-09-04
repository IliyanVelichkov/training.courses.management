package training.courses.management.system.connectivity.lms.client;

import javax.servlet.http.HttpServletResponse;

import training.courses.management.system.commons.util.GsonFactory;
import training.courses.management.system.connectivity.Connector;
import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.lms.client.beans.AddLearningItemRequest;
import training.courses.management.system.connectivity.lms.client.beans.CatalogSearchRequest;
import training.courses.management.system.connectivity.lms.client.beans.CatalogSearchResponse;
import training.courses.management.system.connectivity.lms.client.beans.LMSOnlineCourses;
import training.courses.management.system.connectivity.lms.exception.BadLMSSearchException;

import com.google.gson.Gson;

public class LMSClient {

	private static final String ONLINE_COURSE_CATEGORY = "ONLINE"; //$NON-NLS-1$

	private static final String CATALOG_SEARCH_PATH = "/learning/public-api/rest/v1/current-user/catalogs/~search"; //$NON-NLS-1$
	private static final String ADD_LEARNING_ITEM_PATH = "/learning/public-api/rest/v1/current-user/learning-plan/learning-item"; //$NON-NLS-1$

	private static final String NO_RESULT_ERR_MSG = "No search results for provided search criteria"; //$NON-NLS-1$

	private Connector connector;
	private Gson gson;

	public LMSClient(Connector lmsConnector) {
		this.connector = lmsConnector;
		this.gson = GsonFactory.INSTANCE.createExclusiveGson();
	}

	public CatalogSearchResponse searchOnlineCourses(String searchPhrase) throws BadLMSSearchException, ServiceException {
		String searchJSON = gson.toJson(new CatalogSearchRequest(searchPhrase, ONLINE_COURSE_CATEGORY));
		ServiceHttpResponse resp;
		try {
			resp = connector.postJSON(CATALOG_SEARCH_PATH, searchJSON);
		} catch (ServiceException ex) {
			if (ex.getResponseCode() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
				if (ex.getBody().contains(NO_RESULT_ERR_MSG)) {
					throw new BadLMSSearchException("Missing online courses for search phrase " + searchPhrase); //$NON-NLS-1$
				}
			}
			throw ex;
		}
		return gson.fromJson(resp.getBody(), CatalogSearchResponse.class);
	}

	public ServiceHttpResponse addCourseToLearningPlan(LMSOnlineCourses course) throws ServiceException {
		String request = gson.toJson(AddLearningItemRequest.createFromCourse(course));
		return connector.postJSON(ADD_LEARNING_ITEM_PATH, request);
	}
}
