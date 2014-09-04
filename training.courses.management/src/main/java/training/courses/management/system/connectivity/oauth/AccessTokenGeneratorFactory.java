package training.courses.management.system.connectivity.oauth;

import java.net.URI;
import java.net.URISyntaxException;

import training.courses.management.system.configuration.PasswordAlias;
import training.courses.management.system.configuration.PasswordsKeeper;
import training.courses.management.system.configuration.PasswordsKeeperFactory;
import training.courses.management.system.connectivity.lms.oauth.LMSUserType;
import training.courses.management.system.connectivity.lms.oauth.LMSV1AccessTokenGenerator;
import training.courses.management.system.connectivity.lms.oauth.beans.LMSOAuthCredentials;

public enum AccessTokenGeneratorFactory {
	INSTANCE;

	private PasswordsKeeper passKeeper;

	private AccessTokenGeneratorFactory() {
		this.passKeeper = PasswordsKeeperFactory.INSTANCE.getPasswordsKeeper();
	}

	public AccessTokenGenerator createLMSAccessTokenGenerator(String userId) {
		URI lmsURI;
		String lmsURIPropery = passKeeper.getPassword(PasswordAlias.LMS_URL.getName());
		try {
			lmsURI = new URI(lmsURIPropery);
		} catch (URISyntaxException ex) {
			String errMsg = String.format("Invali value [%s] for property [%s]", PasswordAlias.LMS_URL.getName(), lmsURIPropery); //$NON-NLS-1$
			throw new IllegalStateException(errMsg, ex);
		}
		return new LMSV1AccessTokenGenerator(lmsURI, getLMSCredentials(userId));
	}

	private LMSOAuthCredentials getLMSCredentials(String userId) {
		String companyId = passKeeper.getPassword(PasswordAlias.LMS_COMPANY_ID.getName());
		LMSUserType userType = LMSUserType.ADMIN;
		String clientID = passKeeper.getPassword(PasswordAlias.LMS_CLIENT_ID.getName());
		String clientSecret = passKeeper.getPassword(PasswordAlias.LMS_CLIENT_SECRET.getName());
		return new LMSOAuthCredentials(userId, companyId, userType, clientID, clientSecret);
	}
}
