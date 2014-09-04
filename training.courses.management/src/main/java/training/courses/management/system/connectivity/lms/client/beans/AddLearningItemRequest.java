package training.courses.management.system.connectivity.lms.client.beans;

import com.google.gson.annotations.Expose;

public class AddLearningItemRequest {

	@Expose
	private String componentID;
	@Expose
	private String componentTypeID;
	@Expose
	private long revisionDate;

	public AddLearningItemRequest() {

	}

	public AddLearningItemRequest(String componentID, String componentTypeID, long revisionDate) {
		this.componentID = componentID;
		this.componentTypeID = componentTypeID;
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

	public long getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(long revisionDate) {
		this.revisionDate = revisionDate;
	}

	public static AddLearningItemRequest createFromCourse(LMSOnlineCourses course) {
		return new AddLearningItemRequest(course.getComponentID(), course.getComponentTypeID(), course.getRevisionDate());

	}
}
