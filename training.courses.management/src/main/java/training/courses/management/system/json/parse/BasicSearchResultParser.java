package training.courses.management.system.json.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import training.courses.management.system.beans.Course;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public abstract class BasicSearchResultParser implements SearchResultParser {

	private static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$
	private static final String URL_PATTERN = "\\{(.*?)\\}"; //$NON-NLS-1$

	@Override
	public abstract List<Course> parse(String searchResultJSON, SearchResultParserCfg parserCfg) throws SearchResultParseException;

	protected String[] getFieldToResult(SearchResultParserCfg parserCfg) {
		return parserCfg.getResultPath().split(PATH_SEPARATOR);
	}

	protected Course deserializeCourse(JSONObject courseJsonObject, SearchResultParserCfg parserCfg) throws SearchResultParseException {
		String title = courseJsonObject.optString(parserCfg.getTitleField(), ""); //$NON-NLS-1$
		String descr = courseJsonObject.optString(parserCfg.getDescriptionField(), ""); //$NON-NLS-1$

		String url = createURL(courseJsonObject, parserCfg.getUrlPattern());
		return new Course(title, descr, url);
	}

	private String createURL(JSONObject courseJsonObject, String urlPattern) throws SearchResultParseException {
		List<String> placeholderFields = getUrlPlaceholderFields(urlPattern);
		String url = urlPattern;
		for (String placeholderField : placeholderFields) {
			if (!courseJsonObject.has(placeholderField)) {
				throw new SearchResultParseException(String.format("Missing url field [%s] in course [%s] for pattern ", placeholderField, //$NON-NLS-1$
						courseJsonObject.toString()));
			}
			String value = courseJsonObject.optString(placeholderField, "");
			url = url.replace(String.format("{%s}", placeholderField), value); //$NON-NLS-1$

		}
		return url;
	}

	private List<String> getUrlPlaceholderFields(String urlPattern) throws SearchResultParseException {
		List<String> placeholders = new ArrayList<>();
		Pattern pattern = Pattern.compile(URL_PATTERN);
		Matcher matcher = pattern.matcher(urlPattern);

		while (matcher.find()) {
			placeholders.add(matcher.group(1));
		}
		if (placeholders.size() == 0) {
			throw new SearchResultParseException("Missing url field placeholder for pattern " + urlPattern); //$NON-NLS-1$
		}
		return placeholders;
	}

}
