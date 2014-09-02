package training.courses.management.system.service;

public class ErrorServiceResponse {

	private Object message;

	public ErrorServiceResponse(Object message) {
		this.message = message;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorServiceResponse [message=").append(message).append("]");
		return builder.toString();
	}
}
