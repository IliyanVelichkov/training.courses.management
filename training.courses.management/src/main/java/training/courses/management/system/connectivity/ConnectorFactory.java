package training.courses.management.system.connectivity;

import training.courses.management.system.connectivity.client.CoursesProviderConnector;
import training.courses.management.system.connectivity.lms.client.LMSConnector;
import training.courses.management.system.connectivity.oauth.AccessTokenManager;
import training.courses.management.system.connectivity.oauth.AccessTokenManagerFactory;

public enum ConnectorFactory {
	INSTANCE;

	public Connector createLMSConnector(String userId) {
		AccessTokenManager accessTokenManager = AccessTokenManagerFactory.INSTANCE.createLMSAccessTokenManager(userId);
		return new LMSConnector(accessTokenManager);
	}

	public Connector createCourseProviderConnector(String url) {
		return new CoursesProviderConnector(url);
	}

}
