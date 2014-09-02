package training.courses.management.system.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.util.TestObjectsCreator;

@SuppressWarnings("nls")
public class CoursesProviderValidatorTest {

	private CoursesProvider testCoursesProvider;
	private CoursesProviderValidator validator;

	public CoursesProviderValidatorTest() {
		this.testCoursesProvider = TestObjectsCreator.createValidCoursesProvider();
		this.validator = new CoursesProviderValidator();

	}

	@Test
	public void testValidateWithValidCourseProvider() {
		assertTrue("The course proider must be valid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testFindErrorsWithValidCourseProvider() {
		assertEquals("The course proider must be valid.", 0, validator.findErrors(testCoursesProvider).size());
	}

	@Test
	public void testValidateWithCourseProviderWithNullName() {
		testCoursesProvider.setName(null);
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithEmptyName() {
		testCoursesProvider.setName("");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithSpaceName() {
		testCoursesProvider.setName(" ");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithNullSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern(null);
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithEmptySearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern("");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithSpaceSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern(" ");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithInvalidUrlSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern("http:/example.com");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithValidSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern("https://api.coursera.org/api/catalog.v1/courses?q=search&query={searchPhrase}");
		assertTrue("The course proider must be valid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithMissingSearchPlaceholderInSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern("https://api.coursera.org/api/catalog.v1/courses");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithCourseProviderWithTwoPlaceholderInSearchApiPattern() {
		testCoursesProvider.setSearchApiUrlPattern("https://api.coursera.org/api/catalog.v1/courses{test1}{test2}");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithInvalidNullSearchResultParserCfg() {
		testCoursesProvider.setSearchResultParserCfg(null);
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithMissingSearchPhrasePlaceholderInSearchApi() {
		testCoursesProvider.setSearchApiUrlPattern("http://www.ocwsearch.com/api/v1/search.json?q=&contact=http%3a%2f%2fwww.ocwsearch.com%2fabout");
		assertFalse("The course proider must be invalid.", validator.validate(testCoursesProvider));
	}

	@Test
	public void testValidateWithInvalidFieldsNull() {
		testCoursesProvider.setName(null);
		testCoursesProvider.setSearchApiUrlPattern(null);
		testCoursesProvider.setSearchResultParserCfg(null);
		testCoursesProvider.setUrl(null);
		assertEquals("Invalid errors count", 4, validator.findErrors(testCoursesProvider).size());
	}

}
