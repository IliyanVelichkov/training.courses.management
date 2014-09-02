package training.courses.management.system.connectivity.client;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.beans.Course;
import training.courses.management.system.connectivity.Connector;
import training.courses.management.system.connectivity.ConnectorFactory;
import training.courses.management.system.connectivity.beans.ServiceHttpResponse;
import training.courses.management.system.connectivity.exception.ServiceException;
import training.courses.management.system.json.parse.SearchResultParseException;
import training.courses.management.system.json.parse.SearchResultParser;
import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.validation.CoursesProviderValidator;

public class CoursesProviderClient {

	private static final String URL_PATH_PATTERN = "\\w+\\/(.*)"; //$NON-NLS-1$

	private static final Logger LOGGER = LoggerFactory.getLogger(CoursesProviderClient.class);

	private CoursesProvider courseProvider;
	private Connector connector;
	private SearchResultParser resultParser;

	public CoursesProviderClient(CoursesProvider courseProvider, SearchResultParser resultParser) {
		this.courseProvider = courseProvider;
		this.resultParser = resultParser;

		String url = extractSearchApiUrl(courseProvider.getSearchApiUrlPattern());
		this.connector = ConnectorFactory.INSTANCE.createCourseProviderConnector(url);
	}

	private String extractSearchApiUrl(String searchApiUrlPattern) {
		Pattern pattern = Pattern.compile("(.+?:\\/\\/.*?\\w?\\/)");
		Matcher matcher = pattern.matcher(searchApiUrlPattern);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			String errMsg = String.format("The search api url pattern [%s] is in invalid format!", searchApiUrlPattern);
			LOGGER.error(errMsg);
			throw new IllegalStateException(errMsg);
		}
	}

	public List<Course> searchCourses(String searchPhrase) throws ServiceException, SearchResultParseException {
		String searchPath = buildSearchPath(searchPhrase);

		ServiceHttpResponse response = connector.get(searchPath);
		return resultParser.parse(response.getBody(), courseProvider.getSearchResultParserCfg());
	}

	private String buildSearchPath(String searchPhrase) {
		Pattern pattern = Pattern.compile(URL_PATH_PATTERN);
		Matcher matcher = pattern.matcher(courseProvider.getSearchApiUrlPattern());
		if (!matcher.find()) {
			String errMsg = String.format(
					"Missing search path in the search api url pattern [%s] is in invalid format!", courseProvider.getSearchApiUrlPattern()); //$NON-NLS-1$
			LOGGER.error(errMsg);
			throw new IllegalStateException(errMsg);
		}
		String searchPhrasePlaceholder = String.format("\\{%s\\}", CoursesProviderValidator.SEARCH_PHRASE_PATTERN_PLACEHOLDER); //$NON-NLS-1$
		return matcher.group(1).replaceAll(searchPhrasePlaceholder, searchPhrase);

	}
}
