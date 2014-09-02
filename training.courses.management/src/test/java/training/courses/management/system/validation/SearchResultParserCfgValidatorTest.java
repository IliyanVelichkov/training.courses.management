package training.courses.management.system.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import training.courses.management.system.persistence.model.SearchResultParserCfg;
import training.courses.management.system.util.TestObjectsCreator;

@SuppressWarnings("nls")
public class SearchResultParserCfgValidatorTest {

	private SearchResultParserCfgValidator parserCfgValidator;
	private SearchResultParserCfg testParserCfg;

	public SearchResultParserCfgValidatorTest() {
		this.parserCfgValidator = new SearchResultParserCfgValidator();
		this.testParserCfg = TestObjectsCreator.createValidParserCfg();
	}

	@Test
	public void testCorrectValidation() {
		assertTrue("The configuration must be valid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testCorrectFindErrors() {
		assertEquals("The configuration must have not erros.", 0, parserCfgValidator.findErrors(testParserCfg).size());
	}

	@Test
	public void testValidationWithMissingDescriptionField() {
		testParserCfg.setDescriptionField(null);
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithUnsupportedFieldCharacter() {
		testParserCfg.setDescriptionField("~");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithSupportedFieldCharacterSlash() {
		testParserCfg.setDescriptionField("asd/ds");
		assertTrue("The configuration must be valid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testFindErrorsWithMissingDescriptionField() {
		testParserCfg.setDescriptionField(null);
		testFindErrorsWithOneExpectedField(testParserCfg);
	}

	private void testFindErrorsWithOneExpectedField(SearchResultParserCfg parserCfg) {
		List<String> errors = parserCfgValidator.findErrors(parserCfg);
		assertEquals("Invalid errors counts.", 1, errors.size());
	}

	@Test
	public void testValidationWithEmptyStringDescriptionField() {
		testParserCfg.setDescriptionField("");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithSpaceDescriptionField() {
		testParserCfg.setDescriptionField(" ");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithNullResultFieldType() {
		testParserCfg.setResultFieldType(null);
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithNullTitleFieldType() {
		testParserCfg.setTitleField(null);
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithEmptyStringTitleFieldType() {
		testParserCfg.setTitleField("");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithSpaceTitleFieldType() {
		testParserCfg.setTitleField(" ");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithNullUrlPattern() {
		testParserCfg.setUrlPattern(null);
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithEmptyUrlPattern() {
		testParserCfg.setUrlPattern("");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithSpaceUrlPattern() {
		testParserCfg.setUrlPattern(" ");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithMissingPlaceholdersInUrlPattern() {
		testParserCfg.setUrlPattern("http://example.com");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithInvalidUrlPatternFormat() {
		testParserCfg.setUrlPattern("http:/example.com");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithInvalidUrlPatternFormatWithEmptyPlaceholder() {
		testParserCfg.setUrlPattern("http://example.com/{}");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithValidUrlPatternFormatWithOnePlaceholder() {
		testParserCfg.setUrlPattern("http://example.com/{key1}");
		assertTrue("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testValidationWithInvalidUrlPatternFormatWithTwoPlaceholders() {
		testParserCfg.setUrlPattern("http://example.com/{key1}/{key2}");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testWithNullResultPath() {
		testParserCfg.setResultPath(null);
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testWithEmptyResultPath() {
		testParserCfg.setResultPath("");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testWithSpaceResultPath() {
		testParserCfg.setResultPath(" ");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathEndingWitSlash() {
		testParserCfg.setResultPath("results/");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathStartingWithSlash() {
		testParserCfg.setResultPath("/results");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathStartingWithDoubleSlashes() {
		testParserCfg.setResultPath("results//keys");
		assertFalse("The configuration must be invalid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathRoot() {
		testParserCfg.setResultPath("/");
		assertTrue("The configuration must be valid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathOneLevelDepth() {
		testParserCfg.setResultPath("results");
		assertTrue("The configuration must be valid.", parserCfgValidator.validate(testParserCfg));
	}

	@Test
	public void testResultPathTwoLevelsDepth() {
		testParserCfg.setResultPath("results/keys");
		assertTrue("The configuration must be valid.", parserCfgValidator.validate(testParserCfg));
	}

}
