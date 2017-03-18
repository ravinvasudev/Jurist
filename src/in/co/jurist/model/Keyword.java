package in.co.jurist.model;

/** Representation class for User Search Text. */
public class Keyword {

	private int actId;

	private int amendmentId;

	private int categoryId;

	private int id;

	private String text;

	private int paraId;

	public int getActId() {
		return actId;
	}

	public int getAmendmentId() {
		return amendmentId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public int getId() {
		return id;
	}

	/** Returns Keyword text. */
	public String getText() {
		return text;
	}

	public int getParaId() {
		return paraId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public void setAmendmentId(int amendmentId) {
		this.amendmentId = amendmentId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setParaId(int paraId) {
		this.paraId = paraId;
	}

	@Override
	public String toString() {
		return "Keyword [actId=" + actId + ", amendmentId=" + amendmentId
				+ ", categoryId=" + categoryId + ", id=" + id + ", text="
				+ text + ", paraId=" + paraId + "]";
	}

}
