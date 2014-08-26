package training.courses.management.system.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
public class UserService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Context
	private HttpServletRequest request;

	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@Path("/isUserInRole")
	@GET
	public Response isUserInRole(@QueryParam("role") String role) {
		boolean isUserInRole = request.isUserInRole(role);
		return buildOkResponse(isUserInRole);
	}
}
