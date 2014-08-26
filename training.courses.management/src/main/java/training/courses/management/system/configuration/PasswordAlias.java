package training.courses.management.system.configuration;

//URI lmsURI = new URI("https://acein0184.scdemo.successfactors.com");
//String userId = "NITHY";
//String companyId = "ACEIN0184";
//LMSUserType userType = LMSUserType.ADMIN;
//String clientID = "ACEIN0184";
//String clientSecret = "cf2df3f14ef4a727d017999088d5dc8b003d8a957fae204eda7ca4ac417025259f31fb7683474b8cadb3b65480cd0955";
@SuppressWarnings("nls")
public enum PasswordAlias {
	LMS_URL("lms_url"), LMS_USER_ID("lms_user_id"), LMS_COMPANY_ID("lms_company_id"), LMS_CLIENT_ID("lms_client_id"), LMS_CLIENT_SECRET(
			"lms_client_secret");

	private String name;

	PasswordAlias(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
