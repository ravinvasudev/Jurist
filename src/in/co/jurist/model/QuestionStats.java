package in.co.jurist.model;

import in.co.jurist.util.DateFormatter;

import java.io.Serializable;
import java.util.Calendar;

public class QuestionStats implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int questionId;

	private int voteUpCount;

	private int viewCount;

	private int answerCount;

	private Calendar postDate;

	private String postDateString;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getVoteUpCount() {
		return voteUpCount;
	}

	public void setVoteUpCount(int voteUpCount) {
		this.voteUpCount = voteUpCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public Calendar getPostDate() {
		return postDate;
	}

	public void setPostDate(Calendar postDate) {
		this.postDate = postDate;
		this.postDateString = DateFormatter.getQuestionPostDate(postDate);
	}

	@Override
	public String toString() {
		return "QuestionStats [id=" + id + ", questionId=" + questionId
				+ ", voteUpCount=" + voteUpCount + ", viewCount=" + viewCount
				+ ", answerCount=" + answerCount + ", postDate=" + postDate
				+ "]";
	}

	public String getPostDateString() {
		return postDateString;
	}

	public void setPostDateString(String postDateString) {
		this.postDateString = postDateString;
	}

}
