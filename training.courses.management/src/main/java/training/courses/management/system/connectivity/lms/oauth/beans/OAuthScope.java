package training.courses.management.system.connectivity.lms.oauth.beans;

public class OAuthScope {

	private String userId;
	private String companyId;
	private String userType;
	private String resourceType;

	public OAuthScope(String userId, String companyId, String userType, String resourceType) {
		this.userId = userId;
		this.companyId = companyId;
		this.userType = userType;
		this.resourceType = resourceType;
	}
}
