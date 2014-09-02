package training.courses.management.system.validation;

import java.util.List;

public interface Validator<T> {

	List<String> findErrors(T validationObject);

	boolean validate(T validationObject);

}
