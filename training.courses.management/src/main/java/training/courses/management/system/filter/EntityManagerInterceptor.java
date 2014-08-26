package training.courses.management.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import training.courses.management.system.persistence.manager.EntityManagerProvider;

public class EntityManagerInterceptor implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			EntityManagerProvider.INSTANCE.initEntityManagerProvider();
			chain.doFilter(request, response);
		} finally {
			EntityManagerProvider.INSTANCE.closeEntityManager();
		}
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}
}
