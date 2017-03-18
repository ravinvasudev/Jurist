package in.co.jurist.dao;

import java.util.List;

import in.co.jurist.model.Answer;
import in.co.jurist.model.Comment;
import in.co.jurist.model.Question;
import in.co.jurist.util.Constants.VoteType;

public interface ForumDao {

	/*
	 * Submit Question.
	 * 
	 * @param question
	 * 
	 * @return questionId if success, 0 otherwise
	 */
	public long postQuestion(Question question);

	public List<Question> loadForumQuestions(int page);

	public List<Question> searchForum(String query, int page);

	public int postAnswer(Answer answer);

	public Question getQuestion(long questionId);

	public int voteQuestion(int questionId, int userId);

	public int voteAnswer(int answerId, int userId);

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
	public int isVoted(int id, VoteType type, int userId);

	/**
	 * Adds Answer Comment for specific answer by specific user.
	 * 
	 * @param comment
	 * @return 1 if added successfully, 0 otherwise
	 */
	public int addComment(Comment comment);

}
