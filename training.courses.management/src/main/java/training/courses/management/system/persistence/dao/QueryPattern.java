package training.courses.management.system.persistence.dao;

public enum QueryPattern {

	GET_ALL_ENTITIES("SELECT t FROM %s t"); //$NON-NLS-1$

	private String pattern;

	private QueryPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return this.pattern;
	}

}
