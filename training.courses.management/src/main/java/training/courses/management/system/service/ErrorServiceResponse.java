package training.courses.management.system.service;

public class ErrorServiceResponse {

	private String message;

	public ErrorServiceResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
