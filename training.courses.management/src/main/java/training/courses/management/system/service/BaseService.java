package training.courses.management.system.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

public abstract class BaseService {

	abstract protected Logger getLogger();

	protected Response buildErrResponse(String errorMessage, Throwable cause) {
		getLogger().error(errorMessage, cause);
		return Response.serverError().entity(new ErrorServiceResponse(errorMessage)).build();
	}

	protected Response buildOkResponse() {
		return Response.ok().build();
	}

	protected Response buildOkResponse(Object body) {
		getLogger().debug(String.format("Returning response [%s]", body)); //$NON-NLS-1$
		return Response.ok(body).build();
	}

	protected Response buildBadRequestResponse(Object object) {
		ErrorServiceResponse err = new ErrorServiceResponse(object);
		getLogger().debug(err.toString());
		return Response.status(Status.BAD_REQUEST).entity(err).build();
	}
}
