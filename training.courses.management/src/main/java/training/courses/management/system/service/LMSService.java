package training.courses.management.system.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.lms.client.LMSClient;
import training.courses.management.system.connectivity.lms.client.LMSClientProvider;
import training.courses.management.system.connectivity.lms.client.beans.CatalogSearchResponse;
import training.courses.management.system.connectivity.lms.client.beans.LMSOnlineCourses;
import training.courses.management.system.connectivity.lms.exception.BadLMSSearchException;

@Path("/LMS")
public class LMSService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Context
	private HttpServletRequest request;

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@GET
	@Path("/onlineCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailabeOnlineCourses(@QueryParam("searchPhrase") String searchPhrase) {
		CatalogSearchResponse searchResponse;
		LMSClient lmsClient = getClient();
		try {
			searchResponse = lmsClient.searchOnlineCourses(searchPhrase);
		} catch (ServiceException ex) {
			return buildErrResponse("Failed to get available LMS training courses.", ex); //$NON-NLS-1$
		} catch (BadLMSSearchException e) {
			return buildBadRequestResponse("Missing online courses with search phrase " + searchPhrase); //$NON-NLS-1$
		}
		return buildOkResponse(searchResponse.getOperationStatus().getData().getCourses());
	}

	@POST
	@Path("/learningPlanCourses")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCourseToCurrentUserLearningPlan(LMSOnlineCourses course) {
		LMSClient lmsClient = getClient();
		try {
			ServiceHttpResponse response = lmsClient.addCourseToLearningPlan(course);
			logger.info(String.format("Adding course [%s] Response: [%s]", course, response));
		} catch (ServiceException ex) {
			return buildErrResponse("Failed to add course to current user learning plan.", ex); //$NON-NLS-1$
		}
		return buildOkResponse();
	}

	private LMSClient getClient() {
		return LMSClientProvider.INSTANCE.getClient(request.getRemoteUser());
	}
}
