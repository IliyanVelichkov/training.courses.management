package training.courses.management.system.connectivity.lms.oauth.beans;

import training.courses.management.system.connectivity.lms.oauth.LMSUserType;

public class LMSOAuthCredentials {

	private String userId;
	private String companyId;
	private LMSUserType userType;
	private String clientId;
	private String clientSecret;

	public LMSOAuthCredentials(String userId, String companyId, LMSUserType userType, String clientID, String clientSecret) {
		this.userId = userId;
		this.companyId = companyId;
		this.userType = userType;
		this.clientId = clientID;
		this.clientSecret = clientSecret;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public LMSUserType getUserType() {
		return userType;
	}

	public void setUserType(LMSUserType userType) {
		this.userType = userType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
