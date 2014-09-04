package training.courses.management.system.configuration;

@SuppressWarnings("nls")
public enum PasswordAlias {
	LMS_URL("lms_url"), LMS_COMPANY_ID("lms_company_id"), LMS_CLIENT_ID("lms_client_id"), LMS_CLIENT_SECRET("lms_client_secret");

	private String name;

	PasswordAlias(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
