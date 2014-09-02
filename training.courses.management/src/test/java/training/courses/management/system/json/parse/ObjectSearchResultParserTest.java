package training.courses.management.system.json.parse;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import training.courses.management.system.beans.Course;
import training.courses.management.system.json.parse.ObjectSearchResultParser;
import training.courses.management.system.json.parse.SearchResultParseException;
import training.courses.management.system.persistence.model.ResultFieldType;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public class ObjectSearchResultParserTest {

	private static final String ONE_LEVEL_JSON_RESULT = "{\"name\": \"\",\"result\": {\"1\": {\"title\": \"title1\",\"description\": \"description1\", \"key\": \"key1\"},\"2\": {\"title\": \"title2\",\"description\": \"description2\", \"key\": \"key2\"}}}";
	private static final String ROOT_LEVEL_JSON_RESULT = "{\"1\": {\"title\": \"title1\",\"description\": \"description1\", \"key\": \"key1\"},\"2\": {\"title\": \"title2\",\"description\": \"description2\", \"key\": \"key2\"}}";

	private ObjectSearchResultParser parser = new ObjectSearchResultParser();

	@Test
	public void testParseWithOneLevelResult() throws SearchResultParseException {
		SearchResultParserCfg parserCfg = new SearchResultParserCfg(ResultFieldType.OBJECT, "result", "title", "descrption",
				"http://test/courses/{key}");
		List<Course> courses = parser.parse(ONE_LEVEL_JSON_RESULT, parserCfg);
		assertEquals("Invalid response length.", 2, courses.size());
	}

	@Test
	public void testParseWithRootLevelResult() throws SearchResultParseException {
		SearchResultParserCfg parserCfg = new SearchResultParserCfg(ResultFieldType.OBJECT, "/", "title", "descrption", "http://test/courses/{key}");
		List<Course> courses = parser.parse(ROOT_LEVEL_JSON_RESULT, parserCfg);
		assertEquals("Invalid response length.", 2, courses.size());
	}
}
