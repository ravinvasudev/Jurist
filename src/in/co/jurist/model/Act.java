package in.co.jurist.model;

import in.co.jurist.util.DateFormatter;

import java.io.Serializable;
import java.util.Date;

public class Act implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date announcedDate;

	private String announcedDateString;

	// private int categoryId;

	private String description;

	private int id;

	private String name;

	private String number;

	private int totalAmendments;

	private int totalParagraphs;

	public Act() {
	}

	public Date getAnnouncedDate() {
		return announcedDate;
	}

	public String getAnnouncedDateString() {
		return announcedDateString;
	}

	// public int getCategoryId() {
	// return categoryId;
	// }

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getNumber() {
		return number;
	}

	public int getTotalAmendments() {
		return totalAmendments;
	}

	public int getTotalParagraphs() {
		return totalParagraphs;
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

	// public void setCategoryId(int categoryId) {
	// this.categoryId = categoryId;
	// }

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setTotalAmendments(int totalAmendments) {
		this.totalAmendments = totalAmendments;
	}

	public void setTotalParagraphs(int totalParagraphs) {
		this.totalParagraphs = totalParagraphs;
	}

	@Override
	public String toString() {
		return "Act [announcedDate=" + announcedDate + ", announcedDateString="
				+ announcedDateString + ", description=" + description
				+ ", id=" + id + ", name=" + name + ", number=" + number + "]";
	}

}