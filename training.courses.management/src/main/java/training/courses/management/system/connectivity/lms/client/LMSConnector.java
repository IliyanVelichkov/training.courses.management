package training.courses.management.system.connectivity.lms.client;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.configuration.PasswordAlias;
import training.courses.management.system.configuration.PasswordsKeeperFactory;
import training.courses.management.system.connectivity.HttpConnector;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.oauth.AccessTokenGenerationException;
import training.courses.management.system.connectivity.oauth.AccessTokenManager;

public class LMSConnector extends HttpConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(LMSConnector.class);
	private static final String BEARER_PREFIX = "Bearer "; //$NON-NLS-1$

	private AccessTokenManager accessTokenManager;

	public LMSConnector(AccessTokenManager accessTokenManager) {
		super(PasswordsKeeperFactory.INSTANCE.getPasswordsKeeper().getPassword(PasswordAlias.LMS_URL.getName()));
		this.accessTokenManager = accessTokenManager;
	}

	@Override
	protected Header createAuthHeader() throws ServiceException {
		return createBearerHeader();
	}

	private Header createBearerHeader() throws ServiceException {
		try {
			generateNewAccessTokenIfExpired();
		} catch (AccessTokenGenerationException ex) {
			String errMsg = "Failed to generate access token"; //$NON-NLS-1$
			LOGGER.error(errMsg, ex);
			throw new ServiceException(errMsg, ex);

		}
		return new BasicHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessTokenManager.getAccessToken());
	}

	private void generateNewAccessTokenIfExpired() throws AccessTokenGenerationException {
		if (accessTokenManager.isTokenExpired()) {
			accessTokenManager.generateNewAccessToken();
		}
	}
}
