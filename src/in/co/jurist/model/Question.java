package in.co.jurist.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String text;

	private List<Tag> tags;

	private List<Answer> answers;

	private QuestionStats stats;

	private UserAccount userInfo;

	public Question() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestionStats getStats() {
		return stats;
	}

	public void setStats(QuestionStats stats) {
		this.stats = stats;
	}

	public UserAccount getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserAccount userInfo) {
		this.userInfo = userInfo;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", text=" + text
				+ ", tags=" + tags + ", answers=" + answers + ", stats="
				+ stats + ", userInfo=" + userInfo + "]";
	}

}