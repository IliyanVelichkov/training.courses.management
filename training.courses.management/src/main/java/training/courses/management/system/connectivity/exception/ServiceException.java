package training.courses.management.system.connectivity.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private Integer responseCode;

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message, int responseCode) {
		super(message);
		this.responseCode = responseCode;
	}

	public Integer getResponseCode() {
		return responseCode;
	}
}
