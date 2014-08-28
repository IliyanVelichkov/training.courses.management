package training.courses.management.system.api.commons.json.parse;

import java.util.List;

import training.courses.management.system.beans.Course;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public interface SearchResultParser {

	List<Course> parse(String searchResultJSON, SearchResultParserCfg parserCfg) throws SearchResultParseException;

}
