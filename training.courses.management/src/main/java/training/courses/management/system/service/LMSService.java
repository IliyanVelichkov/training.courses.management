package training.courses.management.system.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.lms.client.LMSClient;

@Path("/LMS")
public class LMSService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private LMSClient lmsClient;

	public LMSService() {
		this.lmsClient = new LMSClient();
	}

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@GET
	@Path("/trainingCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailabeTrainingCourses(@QueryParam("searchPhrase") String searchPhrase) {
		String searchResponseJSON;
		try {
			searchResponseJSON = lmsClient.searchOnlineCourses(searchPhrase);
		} catch (ServiceException ex) {
			return buildErrResponse("Failed to get available LMS training courses.", ex); //$NON-NLS-1$
		}
		return buildOkResponse(searchResponseJSON);
	}

}
