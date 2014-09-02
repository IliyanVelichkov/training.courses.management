package training.courses.management.system.validation;

import java.util.ArrayList;
import java.util.List;

import training.courses.management.system.persistence.model.SearchResultParserCfg;

@SuppressWarnings("nls")
public class SearchResultParserCfgValidator extends BasicValidator implements Validator<SearchResultParserCfg> {

	@Override
	public boolean validate(SearchResultParserCfg parserCfg) {
		return findErrors(parserCfg).size() == 0;
	}

	@Override
	public List<String> findErrors(SearchResultParserCfg parserCfg) {
		List<String> errors = new ArrayList<>();
		if (parserCfg == null) {
			errors.add(ValidatorsMessages.SEARCH_RESULT_PARSER_NULL);
			return errors;
		}

		if (parserCfg.getResultFieldType() == null) {
			errors.add(ValidatorsMessages.RESULT_FIELD_TYPE_NULL);
		}

		errors.addAll(findSimpleStringFieldErrors("description field", parserCfg.getDescriptionField()));
		errors.addAll(findSimpleStringFieldErrors("title field", parserCfg.getTitleField()));
		errors.addAll(findCourseLocationPatternErrors(parserCfg.getUrlPattern()));
		errors.addAll(findResultPathErrors(parserCfg.getResultPath()));

		return errors;
	}

	private List<String> findCourseLocationPatternErrors(String pattern) {
		List<String> errors = new ArrayList<>();

		errors.addAll(findInvalidStringFieldErrors("course location url pattern", pattern));
		if (errors.size() != 0) {
			return errors;
		}
		errors.addAll(findPlaceholderErros(pattern));
		return errors;
	}

	private List<String> findResultPathErrors(String resultPath) {
		List<String> errors = new ArrayList<>();
		errors.addAll(findSimpleStringFieldErrors("result path field", resultPath));
		if (errors.size() != 0) {
			return errors;
		}
		if (resultPath.length() == 1) {
			if (resultPath.charAt(0) != '/') {
				errors.add(ValidatorsMessages.INVALID_ROOT_RESULT_PATH);
			}
		} else {
			if (resultPath.endsWith("/")) {
				errors.add(ValidatorsMessages.INVALID_RESULT_PATH_END);
			}
			if (resultPath.startsWith("/")) {
				errors.add(ValidatorsMessages.INVALID_RESULT_PATH_START);
			}
			if (containsMultipleSlashes(resultPath)) {
				errors.add(ValidatorsMessages.INVALID_RESULT_PATH_MULTIPLE_SLASHES);
			}
		}

		return errors;
	}
}
