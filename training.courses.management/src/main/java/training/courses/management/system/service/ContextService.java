package training.courses.management.system.service;

import java.util.List;

import javax.persistence.RollbackException;
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

import training.courses.management.system.persistence.dao.ContextDAO;
import training.courses.management.system.persistence.dao.KeywordsDAO;
import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.Context;
import training.courses.management.system.persistence.model.Keyword;

@Path("/contexts")
public class ContextService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ContextDAO contextDAO;

	private KeywordsDAO keywordsDAO;

	public ContextService() {
		super();
		this.contextDAO = new ContextDAO(EntityManagerProvider.INSTANCE);
		this.keywordsDAO = new KeywordsDAO(EntityManagerProvider.INSTANCE);
	}

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContexts() {
		List<Context> contexts = contextDAO.getAll();
		return buildOkResponse(contexts);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addContext(Context context) {
		if (isInvalidRequest(context)) {
			return buildBadRequestResponse("Invalid request body"); //$NON-NLS-1$
		}

		Context foundCtx = contextDAO.find(Context.class, context.getName());
		if (foundCtx != null) {
			return buildBadRequestResponse("Context with name " + context.getName() + " already exists."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		try {
			contextDAO.saveNew(context, keywordsDAO);
		} catch (RollbackException ex) {
			return buildErrResponse("Maybe the keyword names are not unique", ex);
		}

		return buildOkResponse();
	}

	private boolean isInvalidRequest(Context context) {
		return null == context || StringUtils.isEmpty(context.getName()) || context.getKeywords() == null || context.getKeywords().isEmpty();
	}

	@GET
	@Path("/{contextName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContext(@PathParam("contextName") String contextName) {
		Context foundCtx = contextDAO.find(Context.class, contextName);
		if (null == foundCtx) {
			return buildBadRequestResponse("Missing context with name " + contextName); //$NON-NLS-1$
		}
		return buildOkResponse(foundCtx);
	}

	@DELETE
	@Path("/{contextName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteContext(@PathParam("contextName") String contextName) {
		Context foundCtx = contextDAO.find(Context.class, contextName);
		if (null == foundCtx) {
			return buildBadRequestResponse("Missing context with name " + contextName); //$NON-NLS-1$
		}

		contextDAO.delete(foundCtx);
		return buildOkResponse();
	}

	@PUT
	@Path("/{contextName}/keywords")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putKeywords(@PathParam("contextName") String contextName, List<Keyword> keywords) {
		Context foundCtx = contextDAO.find(Context.class, contextName);
		if (null == foundCtx) {
			return buildBadRequestResponse("Missing context with name " + contextName); //$NON-NLS-1$
		}
		foundCtx.setKeywords(keywords);
		contextDAO.save(foundCtx);

		return buildOkResponse();
	}
}
