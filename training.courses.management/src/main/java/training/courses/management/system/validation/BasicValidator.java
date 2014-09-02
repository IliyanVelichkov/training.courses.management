package training.courses.management.system.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

@SuppressWarnings("nls")
public abstract class BasicValidator {

	private static final String PLACEHOLDER_PATTERN = "\\{(.+?)\\}";

	protected List<String> findSimpleStringFieldErrors(String fieldName, String fieldValue) {
		List<String> errors = new ArrayList<>();
		errors.addAll(findInvalidStringFieldErrors(fieldName, fieldValue));

		if (errors.size() != 0) {
			return errors;
		}

		if (containsUnsupportedFieldCharacters(fieldValue)) {
			errors.add(String.format(ValidatorsMessagesPatterns.UNSUPPORTED_FIELD_CHARACTERS, fieldName));
		}

		return errors;
	}

	protected List<String> findInvalidStringFieldErrors(String fieldName, String fieldValue) {
		List<String> errors = new ArrayList<>();
		if (fieldValue == null) {
			errors.add(String.format(ValidatorsMessagesPatterns.SIMPLE_STRING_FIELD_NULL, fieldName));
			return errors;
		}

		if (isEmpty(fieldValue)) {
			errors.add(String.format(ValidatorsMessagesPatterns.SIMPLE_STRING_FIELD_EMPTY, fieldName));
		}
		return errors;
	}

	protected boolean isEmpty(String field) {
		return StringUtils.isEmpty(field.trim());
	}

	private boolean containsUnsupportedFieldCharacters(String value) {
		char[] chars = value.toCharArray();
		for (char character : chars) {
			if (!isSupportedCharacter(character)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSupportedCharacter(char character) {
		return (character >= '0' && character <= 'z') || character == '/' || character == '_' || character == ' ' || character == '.';
	}

	protected boolean containsMultipleSlashes(String value) {
		Pattern pattern = Pattern.compile("(//+)");
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

	protected List<String> findUrlPatternErrors(String urlField, String urlPattern) {
		List<String> errors = new ArrayList<>();
		errors.addAll(findInvalidStringFieldErrors(urlField, urlPattern));

		if (errors.size() != 0) {
			return errors;
		}

		errors.addAll(findPlaceholderErros(urlPattern));
		errors.addAll(findUrlFormatErrors(urlField, urlPattern));
		return errors;
	}

	List<String> findPlaceholderErros(String urlPattern) {
		List<String> errors = new ArrayList<>();
		int placeholdersCount = getPlaceholdersCount(urlPattern);
		if (placeholdersCount == 0) {
			errors.add(ValidatorsMessages.URL_PATTERN_MISSING_PLACEHOLDER);
			return errors;
		}

		if (placeholdersCount > 1) {
			errors.add(ValidatorsMessages.URL_PATTERN_TOO_MANY_PLACEHOLDERS);
			return errors;
		}
		return errors;
	}

	private int getPlaceholdersCount(String urlPattern) {
		Pattern pattern = Pattern.compile(PLACEHOLDER_PATTERN);
		Matcher matcher = pattern.matcher(urlPattern);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	protected List<String> findUrlFormatErrors(String fieldName, String value) {
		List<String> errors = new ArrayList<>();

		if (!UrlValidator.getInstance().isValid(value.replaceAll(PLACEHOLDER_PATTERN, ""))) {

			errors.add(String.format(ValidatorsMessagesPatterns.URL_FORMAT_INVALID, fieldName));
		}
		return errors;
	}
}
