package training.courses.management.system.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.Keyword;

public class KeywordsDAO extends BasicDAO<Keyword> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextDAO.class);

	public KeywordsDAO(EntityManagerProvider emProvider) {
		super(emProvider);
	}
}
