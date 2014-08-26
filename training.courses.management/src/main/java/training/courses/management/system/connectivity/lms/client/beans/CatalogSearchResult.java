package training.courses.management.system.connectivity.lms.client.beans;

import java.util.Date;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class CatalogSearchResult {

	private String inventoryType;

	@SerializedName("Sku")
	private String sku;
	private String componentTypeID;
	private String componentTypeDesc;
	private String componentID;
	// TODO this field is TIMESTAMP
	private Date revisionDate;
	private String componentClassification;
	private String qualID;

	// TODO String?
	@SerializedName("Price")
	private String price;
	@SerializedName("addUser")
	private String enroledUser;
	private String addUsername;
	private String isBlendedLearningRegisterRequired;
	private boolean enableOrder;
	private boolean scheduleCanOverridePrice;
	private boolean catalogItemFlagID;
	private boolean catalogItemFlagReason;
	private boolean isRatingEnabled;
	private boolean hasCurriculaAssigned;
	private boolean hasOnlinePart;
	private boolean isPrerequisitesSatisfied;
	private boolean hasVLSInsession;
	private boolean hasPertinentScheduleSegmentInSession;
	private boolean hasPertinentScheduleStarted;
	private boolean isCheckListEnabled;
	private boolean isObserversNominated;
	private boolean isChecklistObservationStarted;
	private boolean isUserTheChecklistObservee;
	private String chargebackMethod;
	private String componentSource;
	private String enrollmentStatusType;
	@SerializedName("Title")
	private String title;
	@SerializedName("Description")
	private String description;
	private String titleLabelKey;
	private String descriptionLabelKey;
	@SerializedName("hasPriceInPreferredCurrency")
	private double priceInPreferredCurrency;
	private double averageRating;
	private long totalRating;
	private long pertinentScheduleEnrollmentSeatID;
	private long onlineStatus;
	private long availableScheduleStatus;
	private long studentComponentID;
	private Map<String, Double> ratingMap;
	// TODO enum CatalogItemStatusDTO
	private Date requiredDate;
	// TODO this field is TIMESTAMP
	private String catalogItemStatusDTO;

	public CatalogSearchResult() {

	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getComponentTypeID() {
		return componentTypeID;
	}

	public void setComponentTypeID(String componentTypeID) {
		this.componentTypeID = componentTypeID;
	}

	public String getComponentTypeDesc() {
		return componentTypeDesc;
	}

	public void setComponentTypeDesc(String componentTypeDesc) {
		this.componentTypeDesc = componentTypeDesc;
	}

	public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getComponentClassification() {
		return componentClassification;
	}

	public void setComponentClassification(String componentClassification) {
		this.componentClassification = componentClassification;
	}

	public String getQualID() {
		return qualID;
	}

	public void setQualID(String qualID) {
		this.qualID = qualID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEnroledUser() {
		return enroledUser;
	}

	public void setEnroledUser(String enroledUser) {
		this.enroledUser = enroledUser;
	}

	public String getAddUsername() {
		return addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

	public String getIsBlendedLearningRegisterRequired() {
		return isBlendedLearningRegisterRequired;
	}

	public void setIsBlendedLearningRegisterRequired(String isBlendedLearningRegisterRequired) {
		this.isBlendedLearningRegisterRequired = isBlendedLearningRegisterRequired;
	}

	public boolean isEnableOrder() {
		return enableOrder;
	}

	public void setEnableOrder(boolean enableOrder) {
		this.enableOrder = enableOrder;
	}

	public boolean isScheduleCanOverridePrice() {
		return scheduleCanOverridePrice;
	}

	public void setScheduleCanOverridePrice(boolean scheduleCanOverridePrice) {
		this.scheduleCanOverridePrice = scheduleCanOverridePrice;
	}

	public boolean isCatalogItemFlagID() {
		return catalogItemFlagID;
	}

	public void setCatalogItemFlagID(boolean catalogItemFlagID) {
		this.catalogItemFlagID = catalogItemFlagID;
	}

	public boolean isCatalogItemFlagReason() {
		return catalogItemFlagReason;
	}

	public void setCatalogItemFlagReason(boolean catalogItemFlagReason) {
		this.catalogItemFlagReason = catalogItemFlagReason;
	}

	public boolean isRatingEnabled() {
		return isRatingEnabled;
	}

	public void setRatingEnabled(boolean isRatingEnabled) {
		this.isRatingEnabled = isRatingEnabled;
	}

	public boolean isHasCurriculaAssigned() {
		return hasCurriculaAssigned;
	}

	public void setHasCurriculaAssigned(boolean hasCurriculaAssigned) {
		this.hasCurriculaAssigned = hasCurriculaAssigned;
	}

	public boolean isHasOnlinePart() {
		return hasOnlinePart;
	}

	public void setHasOnlinePart(boolean hasOnlinePart) {
		this.hasOnlinePart = hasOnlinePart;
	}

	public boolean isPrerequisitesSatisfied() {
		return isPrerequisitesSatisfied;
	}

	public void setPrerequisitesSatisfied(boolean isPrerequisitesSatisfied) {
		this.isPrerequisitesSatisfied = isPrerequisitesSatisfied;
	}

	public boolean isHasVLSInsession() {
		return hasVLSInsession;
	}

	public void setHasVLSInsession(boolean hasVLSInsession) {
		this.hasVLSInsession = hasVLSInsession;
	}

	public boolean isHasPertinentScheduleSegmentInSession() {
		return hasPertinentScheduleSegmentInSession;
	}

	public void setHasPertinentScheduleSegmentInSession(boolean hasPertinentScheduleSegmentInSession) {
		this.hasPertinentScheduleSegmentInSession = hasPertinentScheduleSegmentInSession;
	}

	public boolean isHasPertinentScheduleStarted() {
		return hasPertinentScheduleStarted;
	}

	public void setHasPertinentScheduleStarted(boolean hasPertinentScheduleStarted) {
		this.hasPertinentScheduleStarted = hasPertinentScheduleStarted;
	}

	public boolean isCheckListEnabled() {
		return isCheckListEnabled;
	}

	public void setCheckListEnabled(boolean isCheckListEnabled) {
		this.isCheckListEnabled = isCheckListEnabled;
	}

	public boolean isObserversNominated() {
		return isObserversNominated;
	}

	public void setObserversNominated(boolean isObserversNominated) {
		this.isObserversNominated = isObserversNominated;
	}

	public boolean isChecklistObservationStarted() {
		return isChecklistObservationStarted;
	}

	public void setChecklistObservationStarted(boolean isChecklistObservationStarted) {
		this.isChecklistObservationStarted = isChecklistObservationStarted;
	}

	public boolean isUserTheChecklistObservee() {
		return isUserTheChecklistObservee;
	}

	public void setUserTheChecklistObservee(boolean isUserTheChecklistObservee) {
		this.isUserTheChecklistObservee = isUserTheChecklistObservee;
	}

	public String getChargebackMethod() {
		return chargebackMethod;
	}

	public void setChargebackMethod(String chargebackMethod) {
		this.chargebackMethod = chargebackMethod;
	}

	public String getComponentSource() {
		return componentSource;
	}

	public void setComponentSource(String componentSource) {
		this.componentSource = componentSource;
	}

	public String getEnrollmentStatusType() {
		return enrollmentStatusType;
	}

	public void setEnrollmentStatusType(String enrollmentStatusType) {
		this.enrollmentStatusType = enrollmentStatusType;
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

	public String getTitleLabelKey() {
		return titleLabelKey;
	}

	public void setTitleLabelKey(String titleLabelKey) {
		this.titleLabelKey = titleLabelKey;
	}

	public String getDescriptionLabelKey() {
		return descriptionLabelKey;
	}

	public void setDescriptionLabelKey(String descriptionLabelKey) {
		this.descriptionLabelKey = descriptionLabelKey;
	}

	public double getPriceInPreferredCurrency() {
		return priceInPreferredCurrency;
	}

	public void setPriceInPreferredCurrency(double priceInPreferredCurrency) {
		this.priceInPreferredCurrency = priceInPreferredCurrency;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public long getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(long totalRating) {
		this.totalRating = totalRating;
	}

	public long getPertinentScheduleEnrollmentSeatID() {
		return pertinentScheduleEnrollmentSeatID;
	}

	public void setPertinentScheduleEnrollmentSeatID(long pertinentScheduleEnrollmentSeatID) {
		this.pertinentScheduleEnrollmentSeatID = pertinentScheduleEnrollmentSeatID;
	}

	public long getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(long onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public long getAvailableScheduleStatus() {
		return availableScheduleStatus;
	}

	public void setAvailableScheduleStatus(long availableScheduleStatus) {
		this.availableScheduleStatus = availableScheduleStatus;
	}

	public long getStudentComponentID() {
		return studentComponentID;
	}

	public void setStudentComponentID(long studentComponentID) {
		this.studentComponentID = studentComponentID;
	}

	public Map<String, Double> getRatingMap() {
		return ratingMap;
	}

	public void setRatingMap(Map<String, Double> ratingMap) {
		this.ratingMap = ratingMap;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getCatalogItemStatusDTO() {
		return catalogItemStatusDTO;
	}

	public void setCatalogItemStatusDTO(String catalogItemStatusDTO) {
		this.catalogItemStatusDTO = catalogItemStatusDTO;
	}

}
