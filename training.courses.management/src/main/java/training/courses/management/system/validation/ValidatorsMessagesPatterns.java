package training.courses.management.system.validation;

public final class ValidatorsMessagesPatterns {

	public static final String SIMPLE_STRING_FIELD_NULL = "The %s cannot be null.";
	public static final String SIMPLE_STRING_FIELD_EMPTY = "The %s cannot be empty.";
	public static final String UNSUPPORTED_FIELD_CHARACTERS = "The %s field contains unsupported characters. Only the following characters are allowed: a-z A-Z 0-9 _  / and space character.";
	public static final String URL_FORMAT_INVALID = "The %s field is invalid URL.";

	private ValidatorsMessagesPatterns() {

	}
}
