package training.courses.management.system.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import training.courses.management.system.persistence.manager.EntityManagerProvider;
import training.courses.management.system.persistence.model.Context;
import training.courses.management.system.persistence.model.Keyword;

public class ContextDAO extends BasicDAO<Context> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextDAO.class);

	public ContextDAO(EntityManagerProvider emProvider) {
		super(emProvider);
	}

	public void saveNew(Context context, KeywordsDAO keywordsDAO) {
		List<Keyword> updatedKeywords = updateKeywordsIfAlreadyExists(context, keywordsDAO);
		context.setKeywords(updatedKeywords);

		saveNew(context);
	}

	private List<Keyword> updateKeywordsIfAlreadyExists(Context context, KeywordsDAO keywordsDAO) {
		List<Keyword> updatedKeywords = new ArrayList<>();
		for (Keyword keyword : context.getKeywords()) {
			Keyword foundKeyword = keywordsDAO.find(Keyword.class, keyword.getName());
			if (foundKeyword != null) {
				updatedKeywords.add(foundKeyword);
			} else {
				updatedKeywords.add(keyword);
			}
		}
		return updatedKeywords;
	}
}
