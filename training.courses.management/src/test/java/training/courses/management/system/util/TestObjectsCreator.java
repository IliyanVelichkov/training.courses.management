package training.courses.management.system.util;

import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.persistence.model.ResultFieldType;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public final class TestObjectsCreator {

	private TestObjectsCreator() {
	}

	public static CoursesProvider createValidCoursesProvider() {
		String name = "Coursera";
		String url = "https://www.coursera.org/";
		String searchApiPattern = "https://api.coursera.org/api/catalog.v1/courses?q=search&query={searchPhrase}";

		return new CoursesProvider(name, url, searchApiPattern, createValidParserCfg());
	}

	public static SearchResultParserCfg createValidParserCfg() {
		String resultPath = "elements";
		String urlPattern = "https://www.coursera.org/{shortName}";
		String titleField = "shortName";
		String descriptionField = "name";

		return new SearchResultParserCfg(ResultFieldType.OBJECT, resultPath, titleField, descriptionField, urlPattern);
	}
}
