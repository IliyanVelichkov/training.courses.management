package training.courses.management.system.connectivity.client;

import training.courses.management.system.json.parse.ArraySearchResultParser;
import training.courses.management.system.json.parse.ObjectSearchResultParser;
import training.courses.management.system.persistence.model.CoursesProvider;

public enum CoursesProviderClientFactory {
	INSTANCE;

	public CoursesProviderClient createCoursesProviderClient(CoursesProvider courseProvider) {
		switch (courseProvider.getSearchResultParserCfg().getResultFieldType()) {
		case OBJECT:
			return new CoursesProviderClient(courseProvider, new ObjectSearchResultParser());
		case ARRAY:
			return new CoursesProviderClient(courseProvider, new ArraySearchResultParser());
		default:
			throw new IllegalArgumentException("Invalid course provider " + courseProvider); //$NON-NLS-1$
		}
	}
}
