package in.co.jurist.model;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private String questionId;

	public Tag() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", questionId="
				+ questionId + "]";
	}

}