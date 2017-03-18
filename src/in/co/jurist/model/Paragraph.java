package in.co.jurist.model;

import java.io.Serializable;

public class Paragraph implements Serializable {
	private static final long serialVersionUID = 1L;

	private String abstractText;

	private int actId;

	private String fullText;

	private String header;

	private int id;

	public Paragraph() {

	}

	public String getAbstractText() {
		return this.abstractText;
	}

	public String getFullText() {
		return this.fullText;
	}

	public String getHeader() {
		return this.header;
	}

	public int getId() {
		return this.id;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
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

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	@Override
	public String toString() {
		return "Paragraph [abstractText=" + abstractText + ", actId=" + actId
				+ ", fullText=" + fullText + ", header=" + header + ", id="
				+ id + "]";
	}

}