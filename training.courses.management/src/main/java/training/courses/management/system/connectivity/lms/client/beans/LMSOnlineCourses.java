package training.courses.management.system.connectivity.lms.client.beans;

import com.google.gson.annotations.Expose;

public class LMSOnlineCourses {

	@Expose
	private long revisionDate;
	@Expose
	private String componentID;
	@Expose
	private String componentTypeID;
	@Expose
	private String title;
	@Expose
	private String description;

	public LMSOnlineCourses() {

	}

	public LMSOnlineCourses(long revisionDate, String componentID, String componentTypeID, String title, String description) {
		this.revisionDate = revisionDate;
		this.componentID = componentID;
		this.componentTypeID = componentTypeID;
		this.title = title;
		this.description = description;
	}

	public long getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(long revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}

	public String getComponentTypeID() {
		return componentTypeID;
	}

	public void setComponentTypeID(String componentTypeID) {
		this.componentTypeID = componentTypeID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
