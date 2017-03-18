package in.co.jurist.action;

import in.co.jurist.model.Answer;
import in.co.jurist.model.Comment;
import in.co.jurist.model.Question;
import in.co.jurist.service.ForumService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ForumAction extends ActionSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(ForumAction.class);

	private static final long serialVersionUID = 1L;

	/* Input parameter to receive new submitted Answer. */
	private Answer answer;

	private Comment comment;

	/* Spring injected */
	private ForumService forumService;

	/* Page id to get Questions for Fourm display. */
	private int page;

	/*
	 * Input parameter to receive new submitted Question.
	 * 
	 * Output parameter to return Complete Question for ForumDetails. .
	 */
	private Question question;

	/* Input parameter to get Complete Question for ForumDetails. */
	private long questionId;

	/* Output parameter to return Question List for Forum. */
	private List<Question> questions;

	/* Output parameter. */
	private int status;
	
	private long qstatus;

	/* Input parameter. */
	private String userTags;

	/* Input parameter for question votes. */
	private String voteId;

	/* Input parameter for forum search. */
	private String q;

	public ForumAction() {
		logger.info("ForumAction constructor called.");
	}

	public String addComment() {
		setStatus(getForumService().addComment(getComment()));
		return SUCCESS;
	}

	public Answer getAnswer() {
		return answer;
	}

	public Comment getComment() {
		return comment;
	}

	public ForumService getForumService() {
		return forumService;
	}

	public int getPage() {
		return page;
	}

	public Question getQuestion() {
		return question;
	}

	public long getQuestionId() {
		return questionId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public int getStatus() {
		return status;
	}

	public String getUserTags() {
		return userTags;
	}

	public String getVoteId() {
		return voteId;
	}

	public String loadForum() {
		setQuestions(getForumService().loadForumQuestions(getPage()));

		if (getQuestions() != null && !getQuestions().isEmpty()) {
			return SUCCESS;
		}
		setStatus(0);
		return NONE;
	}

	public String searchForum() {
		setQuestions(getForumService().searchForum(getQ(), getPage()));

		if (getQuestions() != null && !getQuestions().isEmpty()) {
			return SUCCESS;
		}
		setStatus(0);
		return NONE;
	}

	public String loadQuestion() {
		setQuestion(getForumService().getQuestion(getQuestionId()));
		return SUCCESS;
	}

	public String postAnswer() {
		setStatus(getForumService().postAnswer(getAnswer()));
		return SUCCESS;
	}

	public String postQuestion() {
		setQstatus(getForumService().postQuestion(getQuestion(), getUserTags()));
		return SUCCESS;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void setStatus(int status) {
		logger.info("Status[" + status + "]");
		this.status = status;
	}

	public void setUserTags(String userTags) {
		this.userTags = userTags;
	}

	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

	public String voteAnswer() {
		setStatus(getForumService().voteAnswer(getVoteId()));
		return SUCCESS;
	}

	public String voteQuestion() {
		setStatus(getForumService().voteQuestion(getVoteId()));
		return SUCCESS;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public long getQstatus() {
		return qstatus;
	}

	public void setQstatus(long qstatus) {
		this.qstatus = qstatus;
	}

}
