package training.courses.management.system.setup;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import training.courses.management.system.service.ContextService;
import training.courses.management.system.service.CourseProviderService;
import training.courses.management.system.service.LMSService;
import training.courses.management.system.service.UserService;

public class RestConfig extends Application {
	private Set<Object> singletons = new HashSet<>();

	public RestConfig() {
		singletons.add(new JacksonJsonProvider());
		singletons.add(new ContextService());
		singletons.add(new UserService());
		singletons.add(new LMSService());
		singletons.add(new CourseProviderService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
