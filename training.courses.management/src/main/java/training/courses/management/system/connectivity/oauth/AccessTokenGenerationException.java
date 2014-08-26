package training.courses.management.system.connectivity.oauth;

public class AccessTokenGenerationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AccessTokenGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTokenGenerationException(String message) {
		super(message);
	}

}
