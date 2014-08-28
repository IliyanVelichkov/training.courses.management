package training.courses.management.system.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.persistence.dao.CourseProviderDAO;
import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

@Path("/providers")
public class CourseProviderService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private CourseProviderDAO courseProviderDAO;

	public CourseProviderService() {
		this.courseProviderDAO = new CourseProviderDAO(EntityManagerProvider.INSTANCE);
	}

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseProvider() {
		List<CoursesProvider> courseProviders = courseProviderDAO.getAll();
		return buildOkResponse(courseProviders);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSearchProvider(CoursesProvider courseProvider) {
		if (isInvalidRequest(courseProvider)) {
			return buildBadRequestResponse("Invalid request body"); //$NON-NLS-1$
		}

		CoursesProvider foundCourseProvider = courseProviderDAO.find(CoursesProvider.class, courseProvider.getName());
		if (foundCourseProvider != null) {
			return buildBadRequestResponse("Course provider name " + courseProvider.getName() + " already exists."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		courseProviderDAO.saveNew(courseProvider);
		return buildOkResponse();
	}

	private boolean isInvalidRequest(CoursesProvider courseProvider) {
		SearchResultParserCfg searchResultParserCfg = courseProvider.getSearchResultParserCfg();
		return null == courseProvider || StringUtils.isEmpty(courseProvider.getName()) || courseProvider.getSearchResultParserCfg() == null
				|| courseProvider.getSearchApiUrlPattern() == null || searchResultParserCfg == null
				|| searchResultParserCfg.getDescriptionField() == null || searchResultParserCfg.getResultFieldType() == null
				|| searchResultParserCfg.getResultPath() == null || searchResultParserCfg.getTitleField() == null
				|| searchResultParserCfg.getUrlPattern() == null;
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
	public Response deleteSearchProvider(@PathParam("courseProviderName") String courseProviderName, CoursesProvider courseProvider) {
		if (isInvalidRequest(courseProvider)) {
			return buildBadRequestResponse("Invalid request body"); //$NON-NLS-1$
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

}
