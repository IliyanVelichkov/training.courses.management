package training.courses.management.system.connectivity.lms.client;

import java.util.HashMap;
import java.util.Map;

import training.courses.management.system.connectivity.ConnectorFactory;

public enum LMSClientProvider {
	INSTANCE;

	private static final Map<String, LMSClient> CLIENTS = new HashMap<>();
	private ConnectorFactory connectorFactory = ConnectorFactory.INSTANCE;

	public LMSClient getClient(String userId) {
		LMSClient userLmsClient = CLIENTS.get(userId);
		if (userLmsClient == null) {
			userLmsClient = new LMSClient(connectorFactory.createLMSConnector(userId));
			CLIENTS.put(userId, userLmsClient);
		}
		return userLmsClient;
	}
}
