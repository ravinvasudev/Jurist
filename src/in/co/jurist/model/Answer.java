package in.co.jurist.model;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private int questionId;

	private String text;

	private AnswerStats stats;

	private UserAccount userInfo;

	private List<Comment> comments;

	public Answer() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public UserAccount getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserAccount userInfo) {
		this.userInfo = userInfo;
	}

	public AnswerStats getStats() {
		return stats;
	}

	public void setStats(AnswerStats stats) {
		this.stats = stats;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", questionId=" + questionId + ", text="
				+ text + ", stats=" + stats + ", userInfo=" + userInfo
				+ ", comments=" + comments + "]";
	}

}