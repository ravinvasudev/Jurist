package in.co.jurist.model;

import in.co.jurist.util.Constants.ResultType;

public class SearchResult {

	private int resultId;// act id|paragraph id| amendment id

	private ResultType resultType;// act|paragraph|amendment

	private String actName;// under which act - first item in result display

	private String title;// paragraph header|amendment header

	private String text;// act-description or partial abstract text from
						// paragraph|amendment

	private String announcedDate;// used for act

	private int totalAmendments;

	private int totalParagraphs;

	public int getTotalAmendments() {
		return totalAmendments;
	}

	public void setTotalAmendments(int totalAmendments) {
		this.totalAmendments = totalAmendments;
	}

	public int getTotalParagraphs() {
		return totalParagraphs;
	}

	public void setTotalParagraphs(int totalParagraphs) {
		this.totalParagraphs = totalParagraphs;
	}

	public SearchResult() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public String getAnnouncedDate() {
		return announcedDate;
	}

	public void setAnnouncedDate(String announcedDate) {
		this.announcedDate = announcedDate;
	}

}