package training.courses.management.system.connectivity.lms.oauth;

public enum LMSUserType {
	ADMIN("admin"), USER("user"); //$NON-NLS-1$ //$NON-NLS-2$

	private String type;

	private LMSUserType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
