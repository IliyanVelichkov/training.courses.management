package training.courses.management.system.setup;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.crypto.keystore.api.KeyStoreService;
import com.sap.cloud.crypto.keystore.api.KeyStoreServiceException;
import com.sap.cloud.security.password.PasswordStorage;

public class TrustManager {

	private static final String KEYSTORE_PASS_ALIAS = "cacerts_pass";
	private static final String PASSWORD_STORAGE_RES_NAME = "java:comp/env/PasswordStorage";
	private static final String SSL_ALGORITHM = "TLS";
	private static final String KEYSTORE_NAME = "cacerts";
	private static final String KEYSTORE_SERVICE_RES_NAME = "java:comp/env/KeyStoreService";

	public static void init() {
		Logger logger = LoggerFactory.getLogger(TrustManager.class);
		KeyStoreService keystoreService;
		KeyStore clientKeystore;
		PasswordStorage passwordStorage;

		try {
			Context context = new InitialContext();
			passwordStorage = (PasswordStorage) context.lookup(PASSWORD_STORAGE_RES_NAME);
			keystoreService = (KeyStoreService) context.lookup(KEYSTORE_SERVICE_RES_NAME);
			char[] password = passwordStorage.getPassword(KEYSTORE_PASS_ALIAS);

			clientKeystore = keystoreService.getKeyStore(KEYSTORE_NAME, password);

			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientKeystore, password);

			KeyManager[] keyManagers = kmf.getKeyManagers();

			// get a named keystore without integrity check
			KeyStore trustedCAKeystore = keystoreService.getKeyStore(KEYSTORE_NAME, null);

			TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmfactory.init(trustedCAKeystore);

			javax.net.ssl.TrustManager[] trustManagers = tmfactory.getTrustManagers();

			SSLContext sslContext = SSLContext.getInstance(SSL_ALGORITHM);
			sslContext.init(keyManagers, trustManagers, null);

			SSLContext.setDefault(sslContext);

			logger.warn("Custom keystore is loaded"); //$NON-NLS-1$
		} catch (NamingException e) {
			logger.error("Could not find keystoreService", e); //$NON-NLS-1$
		} catch (KeyStoreServiceException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
			logger.error("Failed to custom keystore", e); //$NON-NLS-1$
		} catch (SecurityException | IllegalArgumentException e) {
			logger.error("Failed to replace trusted authorities for EWS client", e); //$NON-NLS-1$
		}

	}
}
