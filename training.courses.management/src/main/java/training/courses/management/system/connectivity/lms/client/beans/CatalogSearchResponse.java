package training.courses.management.system.connectivity.lms.client.beans;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatalogSearchResponse {

	@Expose
	@SerializedName("restOperationStatusVOX")
	private OperationStatus operationStatus;

	public CatalogSearchResponse() {
	}

	public OperationStatus getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(OperationStatus operationStatus) {
		this.operationStatus = operationStatus;
	}

	public class OperationStatus {

		@Expose
		private List<OperationError> errors;
		@Expose
		private LMSResponseStatus status;
		@Expose
		private RestReturnData data;

		public OperationStatus() {

		}

		public List<OperationError> getErrors() {
			return errors;
		}

		public void setErrors(List<OperationError> errors) {
			this.errors = errors;
		}

		public LMSResponseStatus getStatus() {
			return status;
		}

		public void setStatus(LMSResponseStatus status) {
			this.status = status;
		}

		public RestReturnData getData() {
			return data;
		}

		public void setData(RestReturnData data) {
			this.data = data;
		}

		public class RestReturnData {
			@Expose
			@SerializedName("REST_RETURN_DATA")
			private List<LMSOnlineCourses> courses;

			public RestReturnData() {

			}

			public List<LMSOnlineCourses> getCourses() {
				return courses;
			}

			public void setCourses(List<LMSOnlineCourses> courses) {
				this.courses = courses;
			}

			@Override
			@SuppressWarnings("nls")
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("RestReturnData [courses=").append(courses).append("]");
				return builder.toString();
			}
		}

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("OperationStatus [errors=").append(errors).append(", status=").append(status).append(", data=").append(data).append("]");
			return builder.toString();
		}
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CatalogSearchResponse [operationStatus=").append(operationStatus).append("]");
		return builder.toString();
	}

}
