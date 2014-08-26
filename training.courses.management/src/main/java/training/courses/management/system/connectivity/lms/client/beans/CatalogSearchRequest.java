package training.courses.management.system.connectivity.lms.client.beans;

public class CatalogSearchRequest {

	private String searchPhrase;

	public CatalogSearchRequest() {
	}

	public CatalogSearchRequest(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

}
