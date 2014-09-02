package training.courses.management.system.validation;

import java.util.ArrayList;
import java.util.List;

import training.courses.management.system.persistence.model.CoursesProvider;

@SuppressWarnings("nls")
public class CoursesProviderValidator extends BasicValidator implements Validator<CoursesProvider> {

	public static final String SEARCH_PHRASE_PATTERN_PLACEHOLDER = "searchPhrase";

	private SearchResultParserCfgValidator searchResultParserCfgValidator;

	public CoursesProviderValidator() {
		this.searchResultParserCfgValidator = new SearchResultParserCfgValidator();
	}

	@Override
	public boolean validate(CoursesProvider courseProvider) {
		return findErrors(courseProvider).size() == 0;
	}

	@Override
	public List<String> findErrors(CoursesProvider courseProvider) {
		List<String> errors = new ArrayList<>();

		errors.addAll(findSimpleStringFieldErrors("name", courseProvider.getName()));
		errors.addAll(findSearchApiPathErros(courseProvider.getSearchApiUrlPattern()));
		errors.addAll(findInvalidStringFieldErrors("course provider url", courseProvider.getUrl()));

		errors.addAll(searchResultParserCfgValidator.findErrors(courseProvider.getSearchResultParserCfg()));

		return errors;
	}

	private List<String> findSearchApiPathErros(String searchApiUrlPattern) {
		List<String> errors = new ArrayList<>();
		errors.addAll(findUrlPatternErrors("search api url pattern", searchApiUrlPattern));

		if (errors.size() == 0) {
			if (!searchApiUrlPattern.contains(String.format("{%s}", SEARCH_PHRASE_PATTERN_PLACEHOLDER))) {
				errors.add("Missing search phrase placeholder. Search api url pattern must contains {" + SEARCH_PHRASE_PATTERN_PLACEHOLDER + "}");
			}
		}
		return errors;
	}
}
