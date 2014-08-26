package training.courses.management.system.connectivity.oauth;

import training.courses.management.system.connectivity.lms.oauth.LMSAccessTokenManager;

public enum AccessTokenManagerFactory {
	INSTANCE;

	public AccessTokenManager createLMSAccessTokenManager() {
		return new LMSAccessTokenManager();
	}
}
