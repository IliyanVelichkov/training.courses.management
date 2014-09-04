package training.courses.management.system.connectivity.oauth;

import training.courses.management.system.connectivity.lms.oauth.LMSAccessTokenManager;

public enum AccessTokenManagerFactory {
	INSTANCE;

	public AccessTokenManager createLMSAccessTokenManager(String userId) {
		return new LMSAccessTokenManager(userId);
	}
}
