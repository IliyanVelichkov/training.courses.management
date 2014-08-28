package training.courses.management.system.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEARCH_RESULT_PARSER_CONFIGURATIONS")
public class SearchResultParserCfg {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "RESULT_FIELD_TYPE")
	private ResultFieldType resultFieldType;

	@Column(name = "RESULT_PATH")
	private String resultPath;

	@Column(name = "URL_PATTERN")
	private String urlPattern;

	@Column(name = "TITLE_FIELD")
	private String titleField;

	@Column(name = "DESCRIPTION_FIELD")
	private String descriptionField;

	public SearchResultParserCfg() {

	}

	public SearchResultParserCfg(ResultFieldType resultFieldType, String resultPath, String titleField, String descriptionField, String urlPattern) {
		this.resultFieldType = resultFieldType;
		this.resultPath = resultPath;
		this.titleField = titleField;
		this.descriptionField = descriptionField;
		this.urlPattern = urlPattern;
	}

	public ResultFieldType getResultFieldType() {
		return resultFieldType;
	}

	public void setResultFieldType(ResultFieldType resultFieldType) {
		this.resultFieldType = resultFieldType;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getTitleField() {
		return titleField;
	}

	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}

	public String getDescriptionField() {
		return descriptionField;
	}

	public void setDescriptionField(String descriptionField) {
		this.descriptionField = descriptionField;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
}
