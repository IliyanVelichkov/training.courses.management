package training.courses.management.system.connectivity.oauth;

import training.courses.management.system.connectivity.lms.oauth.beans.AccessTokenResponse;

public interface AccessTokenGenerator {

	AccessTokenResponse obtainAccessTokenResponse() throws AccessTokenGenerationException;
}
