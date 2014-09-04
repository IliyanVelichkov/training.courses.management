package training.courses.management.system.connectivity.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private Integer responseCode;
	private String body;

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message, int responseCode, String body) {
		super(message);
		this.responseCode = responseCode;
		this.setBody(body);
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
