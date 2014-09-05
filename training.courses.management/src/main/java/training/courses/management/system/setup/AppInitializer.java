package training.courses.management.system.setup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

	public AppInitializer() {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		TrustManager.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
