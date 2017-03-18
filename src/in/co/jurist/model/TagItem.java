package in.co.jurist.model;

import java.io.Serializable;

public class TagItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String tag;

	public TagItem() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "TagItem [id=" + id + ", tag=" + tag + "]";
	}

}
