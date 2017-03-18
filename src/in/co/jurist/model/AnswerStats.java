package in.co.jurist.model;

import in.co.jurist.util.DateFormatter;

import java.io.Serializable;
import java.util.Calendar;

public class AnswerStats implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int answerId;

	private int voteUpCount;

	private boolean reviewed;
	
	private Calendar postDate;
	
	private String postDateString;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVoteUpCount() {
		return voteUpCount;
	}

	public void setVoteUpCount(int voteUpCount) {
		this.voteUpCount = voteUpCount;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	@Override
	public String toString() {
		return "AnswerStats [id=" + id + ", answerId=" + answerId
				+ ", voteUpCount=" + voteUpCount + ", reviewed=" + reviewed
				+ "]";
	}

	public Calendar getPostDate() {
		return postDate;
	}

	public void setPostDate(Calendar postDate) {
		this.postDate = postDate;
		this.postDateString = DateFormatter.getQuestionPostDate(postDate);
	}

	public String getPostDateString() {
		return postDateString;
	}

	public void setPostDateString(String postDateString) {
		this.postDateString = postDateString;
	}

}
