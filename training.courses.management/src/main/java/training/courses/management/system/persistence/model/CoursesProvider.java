package training.courses.management.system.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COURSES_PROVIDERS")
public class CoursesProvider {

	@Id
	@Column(name = "NAME")
	private String name;

	@Column(name = "URL")
	private String url;

	@Column(name = "SEARCH_API_URL_PATTERN")
	private String searchApiUrlPattern;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SearchResultParserCfg searchResultParserCfg;

	public CoursesProvider() {

	}

	public CoursesProvider(String name, String url, String searchApiUrlPattern, SearchResultParserCfg searchResultParserCfg) {
		this.name = name;
		this.url = url;
		this.searchResultParserCfg = searchResultParserCfg;
		this.setSearchApiUrlPattern(searchApiUrlPattern);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SearchResultParserCfg getSearchResultParserCfg() {
		return searchResultParserCfg;
	}

	public void setSearchResultParserCfg(SearchResultParserCfg param) {
		this.searchResultParserCfg = param;
	}

	public String getSearchApiUrlPattern() {
		return searchApiUrlPattern;
	}

	public void setSearchApiUrlPattern(String searchApiUrlPattern) {
		this.searchApiUrlPattern = searchApiUrlPattern;
	}

}
