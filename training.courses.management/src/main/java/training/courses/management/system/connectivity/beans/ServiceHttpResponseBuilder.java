package training.courses.management.system.connectivity.beans;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class ServiceHttpResponseBuilder {

	public ServiceHttpResponse build(HttpResponse response) throws ParseException, IOException {
		HttpEntity entity = response.getEntity();
		String body = null;

		if (entity != null) {
			body = EntityUtils.toString(entity);
		}
		int statusCode = response.getStatusLine().getStatusCode();
		return new ServiceHttpResponse(statusCode, body);
	}
}