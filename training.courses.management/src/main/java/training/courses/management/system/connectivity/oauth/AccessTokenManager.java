package training.courses.management.system.connectivity.oauth;

public interface AccessTokenManager {

	void generateNewAccessToken() throws AccessTokenGenerationException;

	boolean isTokenExpired();

	String getAccessToken();
}
