package in.co.jurist.model;

import in.co.jurist.util.DateFormatter;

import java.io.Serializable;
import java.util.Date;

public class Amendment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String abstractText;

	private Date announcedDate;

	private String announcedDateString;

	private String fullText;

	private String header;

	private int id;

	private int paragraphId;

	public Amendment() {

	}

	public String getAbstractText() {
		return this.abstractText;
	}

	public Date getAnnouncedDate() {
		return announcedDate;
	}

	public String getAnnouncedDateString() {
		return announcedDateString;
	}

	public String getFullText() {
		return this.fullText;
	}

	public String getHeader() {
		return header;
	}

	public int getId() {
		return this.id;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public void setAnnouncedDate(Date announcedDate) {
		this.announcedDate = announcedDate;
		this.announcedDateString = DateFormatter
				.changeDateFormat(announcedDate);
	}

	public void setAnnouncedDateString(String announcedDateString) {
		this.announcedDateString = announcedDateString;
		this.announcedDate = DateFormatter
				.changeDateFormat(announcedDateString);
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParagraphId() {
		return paragraphId;
	}

	public void setParagraphId(int paragraphId) {
		this.paragraphId = paragraphId;
	}

	@Override
	public String toString() {
		return "Amendment [abstractText=" + abstractText + ", announcedDate="
				+ announcedDate + ", announcedDateString="
				+ announcedDateString + ", fullText=" + fullText + ", header="
				+ header + ", id=" + id + ", paragraphId=" + paragraphId + "]";
	}

}