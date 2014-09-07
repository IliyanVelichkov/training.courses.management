package training.courses.management.system.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.connectivity.client.CoursesProviderClient;
import training.courses.management.system.connectivity.client.CoursesProviderClientFactory;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.json.parse.SearchResultParseException;
import training.courses.management.system.persistence.dao.CourseProviderDAO;
import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.validation.CoursesProviderValidator;

@Path("/providers")
public class CourseProviderService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private CourseProviderDAO courseProviderDAO;
	private CoursesProviderValidator validator;

	public CourseProviderService() {
		this.courseProviderDAO = new CourseProviderDAO(EntityManagerProvider.INSTANCE);
		this.validator = new CoursesProviderValidator();
	}

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseProviders() {
		List<CoursesProvider> courseProviders = courseProviderDAO.getAll();
		return buildOkResponse(courseProviders);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSearchProvider(CoursesProvider courseProvider) {
		if (!validator.validate(courseProvider)) {
			List<String> errors = validator.findErrors(courseProvider);
			return buildBadRequestResponse(errors);
		}

		CoursesProvider foundCourseProvider = courseProviderDAO.find(CoursesProvider.class, courseProvider.getName());
		if (foundCourseProvider != null) {
			return buildBadRequestResponse("Course provider name " + courseProvider.getName() + " already exists."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		courseProviderDAO.saveNew(courseProvider);
		return buildOkResponse();
	}

	@DELETE
	@Path("/{courseProviderName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSearchProvider(@PathParam("courseProviderName") String courseProviderName) {
		CoursesProvider foundCourseProvider = courseProviderDAO.find(CoursesProvider.class, courseProviderName);
		if (null == foundCourseProvider) {
			return buildBadRequestResponse("Missing course provider with name " + courseProviderName); //$NON-NLS-1$
		}

		courseProviderDAO.delete(foundCourseProvider);
		return buildOkResponse();
	}

	@PUT
	@Path("/{courseProviderName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSearchProvider(@PathParam("courseProviderName") String courseProviderName, CoursesProvider courseProvider) {
		if (!validator.validate(courseProvider)) {
			List<String> errors = validator.findErrors(courseProvider);
			return buildBadRequestResponse(errors);
		}

		if (!courseProviderName.equals(courseProvider.getName())) {
			return buildBadRequestResponse("The course name cannot be changed."); //$NON-NLS-1$
		}

		CoursesProvider foundCourseProvider = courseProviderDAO.find(CoursesProvider.class, courseProviderName);
		if (null == foundCourseProvider) {
			return buildBadRequestResponse("Missing course provider with name " + courseProviderName); //$NON-NLS-1$
		}
		courseProviderDAO.save(foundCourseProvider, courseProvider);

		return buildOkResponse();
	}

	@GET
	@Path("/{courseProviderName}/courses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCourses(@PathParam("courseProviderName") String courseProviderName,
			@QueryParam(CoursesProviderValidator.SEARCH_PHRASE_PATTERN_PLACEHOLDER) String searchPhrase) {
		if (null == searchPhrase) {
			return buildBadRequestResponse("Query parameter searchPhrase is missing"); //$NON-NLS-1$
		}
		CoursesProvider foundCourseProvider = courseProviderDAO.find(CoursesProvider.class, courseProviderName);
		if (null == foundCourseProvider) {
			return buildBadRequestResponse("Missing course provider with name " + courseProviderName); //$NON-NLS-1$
		}

		CoursesProviderClient client = CoursesProviderClientFactory.INSTANCE.createCoursesProviderClient(foundCourseProvider);

		try {
			return buildOkResponse(client.searchCourses(URLEncoder.encode(searchPhrase, "UTF-8")));
		} catch (ServiceException ex) {
			if (ex.getResponseCode() != null && ex.getResponseCode() == HttpServletResponse.SC_NOT_FOUND) {
				return buildBadRequestResponse("Missing search result"); //$NON-NLS-1$
			}
			return buildErrResponse("Course provider connection error.", ex); //$NON-NLS-1$
		} catch (SearchResultParseException ex) {
			return buildErrResponse("Failed to parse course provider resposne.", ex); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Failed to encode the search phrase " + searchPhrase); //$NON-NLS-1$
		}
	}
}
