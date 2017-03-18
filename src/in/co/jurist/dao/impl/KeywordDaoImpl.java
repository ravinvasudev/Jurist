package in.co.jurist.dao.impl;

import in.co.jurist.dao.KeywordDao;
import in.co.jurist.model.Keyword;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordDaoImpl implements KeywordDao {

	private static final Logger logger = LoggerFactory
			.getLogger(KeywordDaoImpl.class);

	private DBService dbService;

	public KeywordDaoImpl() {
		logger.info("KeywordDaoImpl constructor called!");
	}

	@Override
	public List<Keyword> loadAll() {
		List<Keyword> keywordList = null;
		String query = Config.getProperty("Keyword.findAll");
		logger.info("Executing query[" + query + "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {
				keywordList = new ArrayList<Keyword>();
				while (rs.next()) {
					Keyword keyword = new Keyword();
					keyword.setId(rs.getInt("id"));
					keyword.setCategoryId(rs.getInt("categoryId"));
					keyword.setActId(rs.getInt("actId"));
					keyword.setParaId(rs.getInt("paraId"));
					keyword.setAmendmentId(rs.getInt("amendmentId"));
					keyword.setText(rs.getString("text"));

					keywordList.add(keyword);
				}
			} else {
				logger.info("No Keywords returned in ResultSet.");
			}
		} catch (SQLException e) {
			logger.info("SQLException while retrieving Keywords. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return keywordList;
	}

	@Override
	public int createKeyword(Keyword keyword) {
		int newTagId = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Keyword.new");
		logger.info("Executing Query[" + query + "], Parameters[" + keyword
				+ "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query,
							PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setInt(2, keyword.getActId());
			ps.setInt(3, keyword.getParaId());
			ps.setInt(4, keyword.getAmendmentId());
			ps.setString(5, keyword.getText().trim());
			newTagId = ps.executeUpdate();
			if (newTagId != Constants.Status.INITIAL.getStatus()) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						newTagId = keys.getInt(1);
					}
				}
			}
			logger.info("New Tag Insert Status: "
					+ (newTagId == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info("SQLException while creating new keyword. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return newTagId;
	}

	@Override
	public int addKeywordText(Keyword keyword) {
		int status = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Keyword.addText");
		logger.info("Executing Query[" + query + "], Parameters[" + keyword
				+ "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, keyword.getText().trim());
			ps.setInt(2, keyword.getActId());
			ps.setInt(3, keyword.getParaId());
			ps.setInt(4, keyword.getAmendmentId());
			status = ps.executeUpdate();
			logger.info("Keyword Add Status: "
					+ (status == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info("SQLException while creating new keyword. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int deleteKeyworText(int id, String keywordTextToUpdate) {
		int status = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Keyword.deleteText");
		logger.info("Executing Query[" + query + "], ID[" + id
				+ "], keywordTextToUpdate[" + keywordTextToUpdate + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, keywordTextToUpdate);
			ps.setInt(2, id);
			status = ps.executeUpdate();
			logger.info("Keyword Delete Status: "
					+ (status == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info("SQLException while creating new keyword. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public List<String> getResultKeywordMapping(String text) {
		List<String> keywordTokenList = null;
		String query = Config.getProperty("Keyword.findResultKeywordMapping");
		logger.info("Executing query[" + query + "], text[" + text + "]");
		try {
			PreparedStatement statement = getDbService().openConnection()
					.prepareStatement(query);
			statement.setString(1, text.trim());
			ResultSet rs = statement.executeQuery();
			if (rs != null && rs.isBeforeFirst()) {
				keywordTokenList = new ArrayList<String>();
				while (rs.next()) {
					keywordTokenList.add(rs.getString("keywordToken"));
				}
			} else {
				logger.info("No ResultKeywordMapping returned in ResultSet.");
			}
		} catch (SQLException e) {
			logger.info(
					"SQLException while retrieving ResultKeywordMapping. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return keywordTokenList;

	}

	@Override
	public void addResultKeywordMapping(String mapping, String text) {
		String query = Config.getProperty("Keyword.addResultKeywordMapping");
		logger.info("Executing Query[" + query + "], KeywordToken[" + mapping
				+ "], SearchText[" + text + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, mapping);
			ps.setString(2, text);
			int status = ps.executeUpdate();
			ps.close();
			logger.info("Result Keyword Mapping Insert Status: "
					+ (status == 1 ? "SUCCESS" : "FAILURE"));
		} catch (SQLException e) {
			logger.info(
					"SQLException while inserting new result keyword mapping. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
	}

}
