package training.courses.management.system.json.parse;

import training.courses.management.system.persistence.model.ResultFieldType;

public enum SearchResultParserFactory {
	INSTANCE;

	public SearchResultParser createSearchResultParser(ResultFieldType resultFieldType) {
		switch (resultFieldType) {
		case OBJECT:
			return new ObjectSearchResultParser();
		case ARRAY:
			return new ArraySearchResultParser();
		default:
			throw new IllegalArgumentException("Invalid result field type " + resultFieldType); //$NON-NLS-1$
		}
	}
}
