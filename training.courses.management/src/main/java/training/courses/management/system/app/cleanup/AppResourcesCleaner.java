package training.courses.management.system.app.cleanup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import training.courses.management.system.persistence.manager.EntityManagerFactoryProvider;

@WebListener
public class AppResourcesCleaner implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent evnt) {
		EntityManagerFactoryProvider.INSTANCE.close();
	}

}
