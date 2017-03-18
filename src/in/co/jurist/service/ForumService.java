package in.co.jurist.service;

import in.co.jurist.model.Answer;
import in.co.jurist.model.Comment;
import in.co.jurist.model.Question;
import in.co.jurist.util.Constants.VoteType;

import java.util.List;

public interface ForumService {

	/*
	 * Submit Question.
	 * 
	 * @param question
	 * 
	 * @return questionId if success, 0 otherwise
	 */
	public long postQuestion(Question question, String userTags);

	public List<Question> loadForumQuestions(int page);

	public List<Question> searchForum(String query, int page);

	public int postAnswer(Answer answer);

	public Question getQuestion(Long questionId);

	public int voteQuestion(String id);

	public int voteAnswer(String id);

	/** Check if Question or Answer is already voted by User. */
	/**
	 * @param id
	 *            of Question or Answer
	 * @param type
	 *            can be QUESTION or ANSWER
	 * @param userId
	 *            unique id of User
	 * @return true if User has already voted, false otherwise
	 */
	public boolean isVoted(int id, VoteType type, int userId);

	/**
	 * Adds Answer Comment for specific answer by specific user.
	 * 
	 * @param comment
	 * @return 1 if added successfully, 0 otherwise
	 */
	public int addComment(Comment comment);

}
