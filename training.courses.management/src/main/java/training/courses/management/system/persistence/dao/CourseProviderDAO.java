package training.courses.management.system.persistence.dao;

import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.CoursesProvider;
import training.courses.management.system.persistence.model.SearchResultParserCfg;

public class CourseProviderDAO extends BasicDAO<CoursesProvider> {

	public CourseProviderDAO(EntityManagerProvider entityManagerProvider) {
		super(entityManagerProvider);
	}

	public void save(CoursesProvider oldCourseProvider, CoursesProvider newCourseProvider) {
		oldCourseProvider.setSearchApiUrlPattern(newCourseProvider.getSearchApiUrlPattern());
		oldCourseProvider.setUrl(newCourseProvider.getUrl());

		SearchResultParserCfg oldParserCfg = oldCourseProvider.getSearchResultParserCfg();
		SearchResultParserCfg newParserCfg = newCourseProvider.getSearchResultParserCfg();
		oldParserCfg.setDescriptionField(newParserCfg.getDescriptionField());
		oldParserCfg.setResultFieldType(newParserCfg.getResultFieldType());
		oldParserCfg.setResultPath(newParserCfg.getResultPath());
		oldParserCfg.setTitleField(newParserCfg.getTitleField());
		oldParserCfg.setUrlPattern(newParserCfg.getUrlPattern());

		save(oldCourseProvider);
	}

}
