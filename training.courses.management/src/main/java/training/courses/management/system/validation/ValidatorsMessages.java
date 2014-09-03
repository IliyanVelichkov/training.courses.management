package training.courses.management.system.validation;

@SuppressWarnings("nls")
public final class ValidatorsMessages {

	public static final String SEARCH_RESULT_PARSER_NULL = "The search result parser configuration cannot be null.";
	public static final String RESULT_FIELD_TYPE_NULL = "The result field type must be specified.";
	public static final String URL_PATTERN_NULL = "The URL pattern can not be null.";
	public static final String URL_PATTERN_EMPTY = "The URL pattern cannot be empty.";
	public static final String URL_PATTERN_INVALID = "The URL pattern is in invalid URL format.";
	public static final String URL_PATTERN_MISSING_PLACEHOLDER = "Missing key placeholder(s) in the URL pattern.";
	public static final String URL_PATTERN_TOO_MANY_PLACEHOLDERS = "Only one placeholder is supported.";
	public static final String INVALID_ROOT_RESULT_PATH = "The root result path must be specified as /";
	public static final String INVALID_RESULT_PATH_END = "The result path cannot end with /. It is allowed only for root result path.";
	public static final String INVALID_RESULT_PATH_START = "The result path cannot start with /. It is allowed only for root result path.";
	public static final String INVALID_CHARACTERS_IN_RESULT_PATH = "Unsupported characters in the result path.";
	public static final String INVALID_RESULT_PATH_MULTIPLE_SLASHES = "The result path cannot have consecutive slashes.";

	private ValidatorsMessages() {

	}

}
