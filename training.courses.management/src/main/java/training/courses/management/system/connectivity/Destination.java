package training.courses.management.system.connectivity;

public enum Destination {
	LMS("LMS"); //$NON-NLS-1$

	private String destinationName;

	private Destination(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationName() {
		return destinationName;
	}

}
