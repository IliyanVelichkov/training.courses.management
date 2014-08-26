package training.courses.management.system.connectivity.lms.oauth;

import java.util.Date;

import training.courses.management.system.connectivity.lms.oauth.beans.AccessTokenResponse;
import training.courses.management.system.connectivity.oauth.AccessTokenGenerationException;
import training.courses.management.system.connectivity.oauth.AccessTokenGenerator;
import training.courses.management.system.connectivity.oauth.AccessTokenGeneratorFactory;
import training.courses.management.system.connectivity.oauth.AccessTokenManager;

public class LMSAccessTokenManager implements AccessTokenManager {

	private AccessTokenGenerator tokenGenerator;
	private String accessToken;
	private Date tokenExpiresIn;

	public LMSAccessTokenManager() {
		this.tokenGenerator = AccessTokenGeneratorFactory.INSTANCE.createLMSAccessTokenGenerator();
		this.tokenExpiresIn = new Date();
	}

	@Override
	public void generateNewAccessToken() throws AccessTokenGenerationException {
		AccessTokenResponse accessTokenResponse = tokenGenerator.obtainAccessTokenResponse();
		this.tokenExpiresIn = new Date(System.currentTimeMillis() + accessTokenResponse.getExpiresIn());
		this.accessToken = accessTokenResponse.getAccessToken();
	}

	@Override
	public boolean isTokenExpired() {
		return new Date().after(tokenExpiresIn);
	}

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

}
