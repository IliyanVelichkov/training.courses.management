package training.courses.management.system.persistence.dao;

import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.Keyword;

public class KeywordsDAO extends BasicDAO<Keyword> {

	public KeywordsDAO(EntityManagerProvider emProvider) {
		super(emProvider);
	}
}
