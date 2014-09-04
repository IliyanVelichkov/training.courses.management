package training.courses.management.system.connectivity.beans;

public class ServiceHttpResponse {

	private int responseCode;
	private String body;

	public ServiceHttpResponse(int responseCode, String body) {
		this.responseCode = responseCode;
		this.body = body;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getBody() {
		return body;
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceHttpResponse [responseCode=").append(responseCode).append(", body=").append(body).append("]");
		return builder.toString();
	}
}
