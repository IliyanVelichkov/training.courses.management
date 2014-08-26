package training.courses.management.system.connectivity.lms.oauth.beans;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("expires_in")
	private long expiresIn;

	@SerializedName("token_type")
	private String tokenType;

	public AccessTokenResponse(String accessToken, int expiresIn, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessTokenResponse [accessToken=").append(accessToken).append(", expiresIn=").append(expiresIn).append(", tokenType=")
				.append(tokenType).append("]");
		return builder.toString();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
