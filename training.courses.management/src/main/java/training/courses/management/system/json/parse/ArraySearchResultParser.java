package training.courses.management.system.json.parse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import training.courses.management.system.beans.Course;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public class ArraySearchResultParser extends BasicSearchResultParser implements SearchResultParser {

	@Override
	public List<Course> parse(String searchResultJSON, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		JSONArray resultsArray = getResultObject(searchResultJSON, parserCfg);
		List<Course> courses = createCourses(resultsArray, parserCfg);
		return courses;
	}

	private JSONArray getResultObject(String searchResultJSON, SearchResultParserCfg parserCfg) {
		String[] fieldsToResult = getFieldToResult(parserCfg);

		if (fieldsToResult.length == 0) {
			return new JSONArray(searchResultJSON);
		}
		String resultField = fieldsToResult[0];
		JSONObject resultObjectToArray = new JSONObject(searchResultJSON);
		for (int idx = 0; idx < fieldsToResult.length - 1; idx++) {
			resultObjectToArray = resultObjectToArray.getJSONObject(fieldsToResult[idx]);
			resultField = fieldsToResult[idx];
		}
		return resultObjectToArray.getJSONArray(resultField);
	}

	private List<Course> createCourses(JSONArray resultsArray, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		List<Course> curses = new ArrayList<>();
		for (int idx = 0; idx < resultsArray.length(); idx++) {
			Course course = deserializeCourse(resultsArray.getJSONObject(idx), parserCfg);
			curses.add(course);
		}
		return curses;
	}
}
