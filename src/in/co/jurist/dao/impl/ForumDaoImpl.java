package in.co.jurist.dao.impl;

import in.co.jurist.dao.ForumDao;
import in.co.jurist.model.Answer;
import in.co.jurist.model.AnswerStats;
import in.co.jurist.model.Comment;
import in.co.jurist.model.Question;
import in.co.jurist.model.QuestionStats;
import in.co.jurist.model.Tag;
import in.co.jurist.model.UserAccount;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;
import in.co.jurist.util.Constants.VoteType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForumDaoImpl implements ForumDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ForumDaoImpl.class);

	private DBService dbService;

	public ForumDaoImpl() {
		logger.info("ForumDaoImpl constructor called!");
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public long postQuestion(Question question) {
		int status = Status.INITIAL.getStatus();
		String query = Config.getProperty("Forum.post-question");
		logger.info("Executing Query[" + query + "], Parameters[" + question
				+ "]");
		try {
			Connection conn = getDbService().openConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, question.getTitle());
			ps.setString(2, question.getText());
			status = ps.executeUpdate();

			/* If above query executed successfully. */
			if (status != 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						question.setId(keys.getLong(1));
					}
				}
				/* Tags insert status */
				status = addQuestionTags(question, conn);
				if (status != 0) {
					/* Stat insert status */
					status = addQuestionStats(question, conn);
				}
				/* Commit if everything goes fine. */
				if (status != 0) {
					getDbService().commit();
				} else {
					getDbService().rollback();
				}
			}
			logger.info("Post Insert Status: "
					+ (status == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			getDbService().rollback();
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return (status == 0 ? 0 : question.getId());
	}

	private int addQuestionStats(Question question, Connection conn)
			throws SQLException {

		String query = Config.getProperty("Forum.add-ques-stats");
		logger.info("Executing Query[" + query + "], Parameters[" + question
				+ "]");
		int status = 0;
		String tagIds = Constants.COMMA_DELIMITER;
		for (Tag tag : question.getTags()) {
			tagIds += String.valueOf(tag.getId()) + Constants.COMMA_DELIMITER;
		}

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, question.getId());
		ps.setString(2, tagIds);
		ps.setInt(3, question.getUserInfo().getId());
		/*
		 * Calendar cal = Calendar.getInstance(Locale.getDefault());
		 * ps.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));
		 */

		status = ps.executeUpdate();
		ps.close();
		logger.info("Question stat Insert Status: "
				+ (status == 0 ? "FAILURE" : "SUCCESS"));
		return (status == 0 ? 0 : 1);

	}

	public int addQuestionTags(Question question, Connection conn)
			throws SQLException {

		String query = Config.getProperty("Forum.add-tags");
		logger.info("Executing Query[" + query + "], Parameters[" + question
				+ "]");
		int status = 0;
		PreparedStatement ps = null;

		List<Tag> tags = question.getTags();
		for (Tag tag : tags) {

			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, tag.getName());
			ps.setLong(2, question.getId());

			status = ps.executeUpdate();

			logger.info("Tag Insert Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));

			try (ResultSet keys = ps.getGeneratedKeys()) {
				if (keys.next()) {
					tag.setId(keys.getInt(1));
					logger.info("Updated id for Tag[" + tag + "]");
				}
			}
		}
		ps.close();
		return (status == 0 ? 0 : 1);
	}

	@Override
	public List<Question> loadForumQuestions(int page) {

		List<Question> questions = null;

		String query = Config.getProperty("Forum.load-display-questions");
		logger.info("Executing Query[" + query + "], Page Id[" + page + "]");
		try {

			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setInt(1, page);
			ResultSet rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {

				questions = new ArrayList<Question>();

				while (rs.next()) {

					Question question = new Question();
					question.setId(rs.getLong("q_id"));
					question.setTitle(rs.getString("q_title"));
					question.setText(rs.getString("q_text"));

					QuestionStats stats = new QuestionStats();
					stats.setVoteUpCount(rs.getInt("qstat_votes"));
					stats.setViewCount(rs.getInt("qstat_views"));
					stats.setAnswerCount(rs.getInt("qstat_answer_count"));
					long postDate = rs.getTimestamp("qstat_post_date")
							.getTime();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(postDate);
					stats.setPostDate(cal);

					question.setStats(stats);

					UserAccount user = new UserAccount();
					user.setName(rs.getString("question_user"));
					user.setDisplayName(rs.getString("question_by_user"));

					question.setUserInfo(user);

					questions.add(question);
				}
			} else {
				logger.info("No Forum Questions found in Database.");
			}

		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return questions;

	}

	@Override
	public int postAnswer(Answer answer) {

		int status = 0;
		String query = Config.getProperty("Forum.post-answer");
		logger.info("Executing Query[" + query + "], Parameters[" + answer
				+ "]");
		try {
			Connection conn = getDbService().openConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, answer.getQuestionId());
			ps.setString(2, answer.getText());

			status = ps.executeUpdate();

			if (status != 0) {
				status = 0;
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						answer.setId(keys.getLong(1));
					}
				}
				status = addAnswerStats(answer, conn);

				getDbService().commit();
			}
			logger.info("Post Insert Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));
		} catch (SQLException e) {
			getDbService().rollback();
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;

	}

	private int addAnswerStats(Answer answer, Connection conn)
			throws SQLException {

		String query = Config.getProperty("Forum.add-ans-stats");
		logger.info("Executing Query[" + query + "], Parameters[" + answer
				+ "]");

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, answer.getId());
		ps.setInt(2, answer.getUserInfo().getId());
		/*
		 * Calendar cal = Calendar.getInstance(Locale.getDefault());
		 * ps.setTimestamp(3, new Timestamp(cal.getTimeInMillis()));
		 */
		int status = ps.executeUpdate();

		ps.close();

		logger.info("Answer stat Insert Status: "
				+ (status == 0 ? "FAILURE" : "SUCCESS"));

		return status;

	}

	@Override
	public Question getQuestion(long questionId) {

		String query = Config.getProperty("Forum.get-question");
		logger.info("Executing Query[" + query + "], questionId[" + questionId
				+ "]");

		Question question = null;
		Answer answer = null;
		try {

			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setLong(1, questionId);
			ResultSet rs = ps.executeQuery();
			List<Answer> answers = null;
			List<Comment> comments = null;
			if (rs.isBeforeFirst()) {

				boolean flag = true;
				answers = new ArrayList<Answer>();

				long previousAnswerId = 0;

				while (rs.next()) {

					if (flag) {
						flag = false;

						question = new Question();
						question.setId(questionId);
						question.setTitle(rs.getString("q_title"));
						question.setText(rs.getString("q_text"));

						QuestionStats stats = new QuestionStats();
						stats.setVoteUpCount(rs.getInt("qstat_votes"));
						stats.setViewCount(rs.getInt("qstat_views"));
						stats.setAnswerCount(rs.getInt("qstat_answer_count"));

						long postDate = rs.getTimestamp("qstat_post_date")
								.getTime();
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(postDate);
						stats.setPostDate(cal);

						question.setStats(stats);

						UserAccount user = new UserAccount();
						user.setName(rs.getString("question_user"));
						user.setDisplayName(rs.getString("question_by_user"));

						question.setUserInfo(user);

						/*
						 * if (question.getStats().getAnswerCount() > 0) {
						 * answers = new ArrayList<Answer>(); }
						 */
					}
					if (question.getStats().getAnswerCount() > 0) {

						long currentAnswerId = rs.getLong("a_id");
						if (previousAnswerId == 0
								|| previousAnswerId != currentAnswerId) {

							if (previousAnswerId != 0) {
								if (comments != null && !comments.isEmpty()) {
									answer.setComments(comments);
								}
								answers.add(answer);
							}
							previousAnswerId = currentAnswerId;

							comments = new ArrayList<Comment>();
							answer = new Answer();

							answer.setId(currentAnswerId);
							answer.setText(rs.getString("a_text"));

							AnswerStats astat = new AnswerStats();
							astat.setAnswerId(rs.getInt("a_id"));
							// astat.setReviewed();
							astat.setVoteUpCount(rs.getInt("astat_votes"));

							if (rs.getTimestamp("astat_post_date") != null) {
								long postDate = rs.getTimestamp(
										"astat_post_date").getTime();
								Calendar cal = Calendar.getInstance();
								cal.setTimeInMillis(postDate);
								astat.setPostDate(cal);
							}
							UserAccount user = new UserAccount();
							user.setName(rs.getString("answer_user"));
							user.setDisplayName(rs
									.getString("answered_by_user"));

							answer.setStats(astat);
							answer.setUserInfo(user);
						}

						String commentText = rs.getString("comment_text");
						if (commentText != null && !commentText.isEmpty()) {
							Comment comment = new Comment();
							comment.setText(commentText);
							comments.add(comment);
						}

					}
				}

				if (comments != null && !comments.isEmpty()) {
					answer.setComments(comments);
				}
				if (answer != null) {
					answers.add(answer);
				}
				if (answers != null && !answers.isEmpty()) {
					question.setAnswers(answers);
					question.getStats().setAnswerCount(answers.size());
				}
			} else {
				logger.info("Unable to get Question from Database.");
			}

		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		logger.info(question.toString());
		return question;

	}

	@Override
	public int voteQuestion(int questionId, int userId) {

		int status = 0;
		String query = Config.getProperty("Forum.vote-for-question");
		logger.info("Executing Query[" + query + "], Parameters[" + questionId
				+ "]");
		try {
			Connection conn = getDbService().openConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, questionId);
			status = ps.executeUpdate();
			logger.info("Question VoteUp Update Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));
			if (status == Status.SUCCESS.getStatus()) {
				query = Config.getProperty("Forum.voteHistory");
				ps = conn.prepareStatement(query);
				ps.setInt(1, questionId);
				ps.setString(2, VoteType.QUESTION.getVoteType());
				ps.setInt(3, userId);
				status = ps.executeUpdate();
				if (status == Status.SUCCESS.getStatus()) {
					conn.commit();
				} else {
					conn.rollback();
				}
				logger.info("Question VoteUp history Status: "
						+ (status == 0 ? "FAILURE" : "SUCCESS"));
			}
			ps.close();
		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int voteAnswer(int answerId, int userId) {

		int status = 0;
		String query = Config.getProperty("Forum.vote-for-answer");
		logger.info("Executing Query[" + query + "], Parameters[" + answerId
				+ "]");
		try {
			Connection conn = getDbService().openConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, answerId);
			status = ps.executeUpdate();
			logger.info("Answer VoteUp Update Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));
			if (status == Status.SUCCESS.getStatus()) {
				query = Config.getProperty("Forum.voteHistory");
				ps = conn.prepareStatement(query);
				ps.setInt(1, answerId);
				ps.setString(2, VoteType.ANSWER.getVoteType());
				ps.setInt(3, userId);
				status = ps.executeUpdate();
				if (status == Status.SUCCESS.getStatus()) {
					conn.commit();
				} else {
					conn.rollback();
				}
				logger.info("Answer VoteUp history Status: "
						+ (status == 0 ? "FAILURE" : "SUCCESS"));
			}
			ps.close();
		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int isVoted(int id, VoteType type, int userId) {

		int status = 0;
		String query = Config.getProperty("Forum.is-voted");
		logger.info("Executing Query[" + query + "], VoteId[" + id + "], Type["
				+ type.toString() + "], UserId[" + userId + "]");

		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, type.getVoteType());
			ps.setInt(3, userId);

			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.isBeforeFirst()) {
				status = 1;
				ps.close();
			}
			logger.info("{} with ID={} is {}", type.toString(), id,
					(status == 0 ? "NOT VOTED" : "ALREADY VOTED"));

		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;

	}

	@Override
	public int addComment(Comment comment) {

		int status = 0;
		String query = Config.getProperty("Forum.addComment");
		logger.info("Executing Query[" + query + "], Parameters["
				+ comment.toString() + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setInt(1, comment.getAnswerId());
			ps.setInt(2, comment.getUserId());
			ps.setString(3, comment.getText());

			status = ps.executeUpdate();
			logger.info("Comment Insert Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));
			ps.close();
		} catch (SQLException e) {
			getDbService().rollback();
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;

	}

	@Override
	public List<Question> searchForum(String q, int page) {
		List<Question> questions = null;
		String query = "select q.id as q_id, q.title as q_title, q.text as q_text, qstat.vote_up as qstat_votes, qstat.views as qstat_views, (select count(*) from fanswer a where a.question_id = q.id) as qstat_answer_count, qstat.post_date as qstat_post_date,  user.name as question_user, user.display_name as question_by_user from fquestion q, fquestion_stat qstat, user_account user where q.id = qstat.question_id and qstat.user_id = user.id and (";

		String[] qArr = q.split(Constants.SPACE_DELIMITER);
		for (int i = 0; i < qArr.length; i++) {
			if (i != 0) {
				query += Constants.SQL_STRING_OR;
			}
			query += "q.title like '%" + qArr[i] + "%'";
		}
		query += ") order by qstat.post_date desc limit " + page + ", 10";

		// String query = Config.getProperty("Forum.searchForumQuestions");

		logger.info("Executing Query[" + query + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {

				questions = new ArrayList<Question>();

				while (rs.next()) {

					Question question = new Question();
					question.setId(rs.getLong("q_id"));
					question.setTitle(rs.getString("q_title"));
					question.setText(rs.getString("q_text"));

					QuestionStats stats = new QuestionStats();
					stats.setVoteUpCount(rs.getInt("qstat_votes"));
					stats.setViewCount(rs.getInt("qstat_views"));
					stats.setAnswerCount(rs.getInt("qstat_answer_count"));
					long postDate = rs.getTimestamp("qstat_post_date")
							.getTime();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(postDate);
					stats.setPostDate(cal);

					question.setStats(stats);

					UserAccount user = new UserAccount();
					user.setName(rs.getString("question_user"));
					user.setDisplayName(rs.getString("question_by_user"));

					question.setUserInfo(user);

					questions.add(question);
				}
			} else {
				logger.info(
						"No Forum Questions found in Database for Search String {}.",
						q);
			}

		} catch (SQLException e) {
			logger.info("SQLException while executing query. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return questions;
	}
}
