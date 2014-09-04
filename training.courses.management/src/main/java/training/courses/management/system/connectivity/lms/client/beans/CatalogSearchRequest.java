package training.courses.management.system.connectivity.lms.client.beans;

import com.google.gson.annotations.Expose;

public class CatalogSearchRequest {

	@Expose
	private String searchPhrase;

	@Expose
	private String courseCategory;

	public CatalogSearchRequest() {
	}

	public CatalogSearchRequest(String searchPhrase, String courseCategory) {
		this.searchPhrase = searchPhrase;
		this.setCourseCategory(courseCategory);
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

}
