package in.co.jurist.service.impl;

import in.co.jurist.dao.ForumDao;
import in.co.jurist.model.Answer;
import in.co.jurist.model.Comment;
import in.co.jurist.model.Question;
import in.co.jurist.model.Tag;
import in.co.jurist.service.ForumService;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;
import in.co.jurist.util.Constants.VoteType;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForumServiceImpl implements ForumService {

	private static final Logger logger = LoggerFactory
			.getLogger(ForumServiceImpl.class);

	// Spring injected
	private ForumDao forumDao;

	public ForumServiceImpl() {
		logger.info("ForumServiceImpl constructor called.");

	}

	public ForumDao getForumDao() {
		return forumDao;
	}

	public void setForumDao(ForumDao forumDao) {
		this.forumDao = forumDao;
	}

	@Override
	public long postQuestion(Question question, String userTags) {
		assembleTags(question, userTags);
		return getForumDao().postQuestion(question);
	}

	/** Process userTags string, prepares a Tag List and set in Question. */
	private void assembleTags(Question question, String userTags) {
		if (userTags != null && !userTags.isEmpty()) {
			String[] tagNames = userTags.split(Constants.COMMA_DELIMITER);
			if (tagNames.length > 0) {
				List<Tag> tags = new ArrayList<Tag>();
				for (String tagName : tagNames) {
					Tag tag = new Tag();
					tag.setName(tagName);
					tags.add(tag);
				}
				question.setTags(tags);
			}
		}
	}

	@Override
	public List<Question> loadForumQuestions(int page) {

		if (page >= 0) {
			return getForumDao().loadForumQuestions(page);
		}
		return null;
	}

	@Override
	public int postAnswer(Answer answer) {

		return getForumDao().postAnswer(answer);
	}

	@Override
	public Question getQuestion(Long questionId) {

		if (questionId == 0) {
			return null;
		}

		return getForumDao().getQuestion(questionId);

	}

	@Override
	public int voteQuestion(String id) {

		String[] arr = id.split(Constants.HYPHEN_DELIMITER);
		int questionId = Integer.parseInt(arr[0]);
		int userId = Integer.parseInt(arr[1]);

		if (!isVoted(questionId, VoteType.QUESTION, userId)) {
			return getForumDao().voteQuestion(questionId, userId);
		} else {
			return Status.INITIAL.getStatus();
		}

	}

	@Override
	public int voteAnswer(String id) {

		String[] arr = id.split(Constants.HYPHEN_DELIMITER);
		int answerId = Integer.parseInt(arr[0]);
		int userId = Integer.parseInt(arr[1]);

		if (!isVoted(answerId, VoteType.ANSWER, userId)) {
			return getForumDao().voteAnswer(answerId, userId);
		} else {
			return Status.INITIAL.getStatus();
		}

	}

	@Override
	public boolean isVoted(int id, VoteType type, int userId) {
		return getForumDao().isVoted(id, type, userId) == Status.SUCCESS
				.getStatus() ? true : false;
	}

	@Override
	public int addComment(Comment comment) {

		if (comment.getAnswerId() != 0 && comment.getText() != null
				&& comment.getUserId() != 0) {
			return getForumDao().addComment(comment);
		} else {
			logger.info("{} contains invalid data.", comment.toString());
		}
		return 0;

	}

	@Override
	public List<Question> searchForum(String query, int page) {
		if (query != null && !query.isEmpty() && page >= 0) {
			return getForumDao().searchForum(query, page);
		}
		return null;
	}

}
