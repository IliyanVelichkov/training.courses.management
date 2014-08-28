package training.courses.management.system.api.commons.json.parse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import training.courses.management.system.beans.Course;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public class ObjectSearchResultParser extends BasicSearchResultParser implements SearchResultParser {

	public ObjectSearchResultParser() {

	}

	@Override
	public List<Course> parse(String searchResultJSON, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		JSONObject resultsObject = getResultObject(searchResultJSON, parserCfg);
		List<Course> courses = createCourses(resultsObject, parserCfg);
		return courses;
	}

	private JSONObject getResultObject(String searchResultJSON, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		String[] fieldsToResult = getFieldToResult(parserCfg);

		JSONObject resultsObject = new JSONObject(searchResultJSON);
		for (String field : fieldsToResult) {
			resultsObject = resultsObject.getJSONObject(field);
		}

		if (resultsObject == null) {
			throw new SearchResultParseException(String.format("Missing search result object with path [%s]. Search result JSON [%s]", //$NON-NLS-1$
					parserCfg.getResultPath(), searchResultJSON));
		}
		return resultsObject;
	}

	private List<Course> createCourses(JSONObject resultsObject, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		JSONArray coursesFields = resultsObject.names();
		List<Course> courses = new ArrayList<>();
		for (int idx = 0; idx < coursesFields.length(); idx++) {
			JSONObject course = resultsObject.getJSONObject(coursesFields.getString(idx));
			courses.add(deserializeCourse(course, parserCfg));
		}
		return courses;
	}

}
