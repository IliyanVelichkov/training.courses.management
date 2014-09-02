package training.courses.management.system.connectivity.lms.oauth.beans;

import training.courses.management.system.commons.util.GsonFactory;

import com.google.gson.annotations.SerializedName;

public class AccessTokenRequest {

	@SerializedName("grant_type")
	private String grantType;

	@SerializedName("scope")
	private OAuthScope scope;

	@SerializedName("client_id")
	private String clientId;

	@SerializedName("client_secret")
	private String clientSecret;

	public AccessTokenRequest(String grantType, OAuthScope scope, String clientId, String clientSecret) {
		this.grantType = grantType;
		this.scope = scope;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String toJSON() {
		return GsonFactory.INSTANCE.getGson().toJson(this);
	}
}
