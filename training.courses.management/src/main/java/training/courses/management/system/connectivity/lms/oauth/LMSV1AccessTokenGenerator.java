package training.courses.management.system.connectivity.lms.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.api.util.GsonFactory;
import training.courses.management.system.connectivity.lms.oauth.beans.AccessTokenRequest;
import training.courses.management.system.connectivity.lms.oauth.beans.AccessTokenResponse;
import training.courses.management.system.connectivity.lms.oauth.beans.LMSOAuthCredentials;
import training.courses.management.system.connectivity.lms.oauth.beans.OAuthScope;
import training.courses.management.system.connectivity.oauth.AccessTokenGenerationException;
import training.courses.management.system.connectivity.oauth.AccessTokenGenerator;

public class LMSV1AccessTokenGenerator implements AccessTokenGenerator {

	private static final String V1_TOKEN_SERVICE_PATH = "/learning/oauth-api/rest/v1/token"; //$NON-NLS-1$
	private static final String GRANT_TYPE = "client_credentials"; //$NON-NLS-1$
	private static final String RESOURCE_TYPE = "learning_public_api"; //$NON-NLS-1$
	private static final Logger LOGGER = LoggerFactory.getLogger(LMSV1AccessTokenGenerator.class);

	private LMSOAuthCredentials credentials;
	private URI lmsURI;
	private DefaultHttpClient httpClient;

	public LMSV1AccessTokenGenerator(URI lmsURI, LMSOAuthCredentials credentials) {
		this.credentials = credentials;
		this.lmsURI = lmsURI;
		this.httpClient = new DefaultHttpClient();
	}

	@Override
	public AccessTokenResponse obtainAccessTokenResponse() throws AccessTokenGenerationException {
		HttpPost post = new HttpPost(lmsURI + V1_TOKEN_SERVICE_PATH);
		AccessTokenRequest body = createAccessTokeRequest();
		try {
			post.setEntity(new StringEntity(body.toJSON(), StandardCharsets.UTF_8.displayName()));
			HttpResponse response = httpClient.execute(post);
			int responseCode = response.getStatusLine().getStatusCode();
			if (HttpServletResponse.SC_OK != responseCode) {
				throw new AccessTokenGenerationException("Failed to get access token. Invalid response code " + responseCode); //$NON-NLS-1$
			}
			return extractAccessTokenResponse(response);
		} catch (UnsupportedEncodingException ex) {
			throw new AccessTokenGenerationException("Failed to encode access token request.", ex); //$NON-NLS-1$
		} catch (IOException ex) {
			throw new AccessTokenGenerationException("Failed to obtain access token.", ex); //$NON-NLS-1$
		}
	}

	private AccessTokenRequest createAccessTokeRequest() {
		OAuthScope scope = new OAuthScope(credentials.getUserId(), credentials.getCompanyId(), LMSUserType.ADMIN.getType(), RESOURCE_TYPE);
		return new AccessTokenRequest(GRANT_TYPE, scope, credentials.getClientId(), credentials.getClientSecret());
	}

	private AccessTokenResponse extractAccessTokenResponse(HttpResponse httpResponse) throws AccessTokenGenerationException {
		HttpEntity entity = httpResponse.getEntity();
		if (null == entity) {
			throw new AccessTokenGenerationException("Missing response entity."); //$NON-NLS-1$
		}

		try {
			String respStr = EntityUtils.toString(entity);
			AccessTokenResponse response = GsonFactory.INSTANCE.getGson().fromJson(respStr, AccessTokenResponse.class);
			LOGGER.info("Obtained access token response: " + response.toString()); //$NON-NLS-1$
			convertExpiresInToMilliseconds(response);
			return response;
		} catch (ParseException | IOException ex) {
			throw new AccessTokenGenerationException("Failed to extract access token from the response.", ex); //$NON-NLS-1$
		}
	}

	private void convertExpiresInToMilliseconds(AccessTokenResponse response) {
		response.setExpiresIn(response.getExpiresIn() * 1000);
	}

}
