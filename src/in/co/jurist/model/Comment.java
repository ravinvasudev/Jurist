package in.co.jurist.model;

import java.io.Serializable;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	private int answerId;

	private int id;

	private String postDate;

	private String text;

	private int userId;

	public Comment() {

	}

	public int getAnswerId() {
		return answerId;
	}

	public int getId() {
		return id;
	}

	public String getPostDate() {
		return postDate;
	}

	public String getText() {
		return text;
	}

	public int getUserId() {
		return userId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Comment [answerId=" + answerId + ", id=" + id + ", postDate="
				+ postDate + ", text=" + text + ", userId=" + userId + "]";
	}

}