package training.courses.management.system.connectivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.beans.ServiceHttpResponseBuilder;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.connectivity.lms.client.LMSConnector;

public abstract class HttpConnector implements Connector {

	private static final Logger LOGGER = LoggerFactory.getLogger(LMSConnector.class);
	private static final Header JSON_HEADER = new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

	private String url;
	private HttpClient httpClient;
	private ServiceHttpResponseBuilder responseBuilder;

	public HttpConnector(String url) {
		this.url = url;
		this.httpClient = createClient();
		this.responseBuilder = new ServiceHttpResponseBuilder();
	}

	private HttpClient createClient() {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		return client;
	}

	abstract protected Header createAuthHeader() throws ServiceException;

	@Override
	public ServiceHttpResponse get(String resourcePath) throws ServiceException {
		HttpGet getRequest = new HttpGet(buildRequestURI(resourcePath));
		return execute(getRequest);
	}

	@Override
	public ServiceHttpResponse postJSON(String resourcePath, String jsonBody) throws ServiceException {
		HttpPost postRequest = new HttpPost(buildRequestURI(resourcePath));
		try {
			postRequest.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8.displayName()));
		} catch (UnsupportedEncodingException ex) {
			return handleError("Failed to encode the request body for the POST request " + postRequest.getURI(), ex); //$NON-NLS-1$
		}
		postRequest.addHeader(JSON_HEADER);
		return execute(postRequest);
	}

	@Override
	public ServiceHttpResponse putJSON(String resourcePath, String jsonBody) throws ServiceException {
		HttpPut putRequest = new HttpPut(buildRequestURI(resourcePath));
		try {
			putRequest.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8.displayName()));
		} catch (UnsupportedEncodingException ex) {
			return handleError("Failed to encode the request body for the PUT request " + putRequest.getURI(), ex); //$NON-NLS-1$
		}
		putRequest.addHeader(JSON_HEADER);
		return execute(putRequest);
	}

	@Override
	public ServiceHttpResponse delete(String resourcePath) throws ServiceException {
		HttpDelete deleteRequest = new HttpDelete(buildRequestURI(resourcePath));
		return execute(deleteRequest);
	}

	@Override
	public void shutDown() {
		httpClient.getConnectionManager().shutdown();
	}

	private String buildRequestURI(String resourcePath) {
		return url + formatResourcePath(resourcePath);
	}

	private String formatResourcePath(String resourcePath) {
		return resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath; //$NON-NLS-1$
	}

	private ServiceHttpResponse execute(HttpRequestBase request) throws ServiceException {
		HttpResponse response;
		try {
			request.addHeader(createAuthHeader());
			response = httpClient.execute(request);
			validateResponse(response);
			return buildResponse(response);
		} catch (ParseException | IOException ex) {
			return handleError("Failed to execute request to url " + request.getURI(), ex); //$NON-NLS-1$
		} finally {
			request.abort();
		}
	}

	private void validateResponse(HttpResponse response) throws ServiceException {
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode < 200 || responseCode >= 300) {
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String body = getResponseBody(entity);
				String errMsg = String.format("Invalid response. Staus code [%d] Body [%s]", responseCode, body);
				handleError(errMsg);
			}
			String errMsg = String.format("Invalid response. Staus code [%d]", responseCode);
			handleError(errMsg);
		}
	}

	private String getResponseBody(HttpEntity entity) throws ServiceException {
		try {
			return EntityUtils.toString(entity);
		} catch (ParseException | IOException ex) {
			String errMsg = "Failed to get response body."; //$NON-NLS-1$
			LOGGER.error(errMsg, ex);
			throw new ServiceException(errMsg, ex);
		}
	}

	private ServiceHttpResponse buildResponse(HttpResponse response) throws ServiceException {
		try {
			return responseBuilder.build(response);
		} catch (ParseException | IOException ex) {
			return handleError("Failed to build service respose", ex); //$NON-NLS-1$
		}
	}

	private ServiceHttpResponse handleError(String message) throws ServiceException {
		return handleError(message, null);
	}

	private ServiceHttpResponse handleError(String message, Throwable cause) throws ServiceException {
		LOGGER.error(message, cause);
		throw new ServiceException(message, cause);
	}

}
