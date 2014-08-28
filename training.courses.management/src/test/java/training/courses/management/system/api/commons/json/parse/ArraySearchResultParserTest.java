package training.courses.management.system.api.commons.json.parse;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import training.courses.management.system.beans.Course;
import training.courses.management.system.persistence.model.ResultFieldType;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public class ArraySearchResultParserTest {

	private static final String ONE_LEVEL_JSON_RESULT = "{\"name\":\"\",\"result\":[{\"title\":\"title1\",\"description\":\"description1\",\"key\":\"key1\"},{\"title\":\"title2\",\"description\":\"description2\",\"key\":\"key2\"}]}";
	private static final String ROOT_LEVEL_JSON_RESULT = "[{\"title\":\"title1\",\"description\":\"description1\",\"key\":\"key1\"},{\"title\":\"title2\",\"description\":\"description2\",\"key\":\"key2\"}]";

	private ArraySearchResultParser parser = new ArraySearchResultParser();

	@Test
	public void testParseOneLevelResult() throws SearchResultParseException {
		SearchResultParserCfg parserCfg = new SearchResultParserCfg(ResultFieldType.OBJECT, "result", "title", "descrption",
				"http://test/courses/{key}");
		List<Course> courses = parser.parse(ONE_LEVEL_JSON_RESULT, parserCfg);
		assertEquals("Invalid response length.", 2, courses.size());
	}

	@Test
	public void testParseRootLevelResult() throws SearchResultParseException {
		SearchResultParserCfg parserCfg = new SearchResultParserCfg(ResultFieldType.OBJECT, "/", "title", "description",
				"http://test/courses/{key}/{title}");
		List<Course> courses = parser.parse(ROOT_LEVEL_JSON_RESULT, parserCfg);
		assertEquals("Invalid response length.", 2, courses.size());
	}
}
