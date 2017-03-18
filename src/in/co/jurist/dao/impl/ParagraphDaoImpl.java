package in.co.jurist.dao.impl;

import in.co.jurist.dao.ParagraphDao;
import in.co.jurist.model.Paragraph;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphDaoImpl implements ParagraphDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ParagraphDaoImpl.class);

	private DBService dbService;

	public ParagraphDaoImpl() {
		logger.info("ParagraphDaoImpl constructor called!");
	}

	@Override
	public List<Paragraph> loadParagraphHeaders() {
		List<Paragraph> paraList = null;
		String query = Config.getProperty("Paragraph.findAll");
		logger.info("Executing query[" + query + "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {
				paraList = new ArrayList<Paragraph>();
				while (rs.next()) {

					Paragraph para = new Paragraph();

					para.setId(rs.getInt("id"));
					para.setHeader(rs.getString("header"));
					/*
					 * para.setFullText(rs.getString("fText"));
					 * para.setAbstractText(rs.getString("absText"));
					 */

					para.setActId(rs.getInt("actId"));
					paraList.add(para);
				}
			} else {
				logger.info("No Acts returned in ResultSet.");
			}
		} catch (SQLException e) {
			logger.info("SQLException while retrieving Acts. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return paraList;
	}

	@Override
	public int createParagraph(Paragraph paragraph) {
		int newSectionId = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Paragraph.new");
		logger.info("Executing Query[" + query + "], Parameters[" + paragraph
				+ "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query,
							PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, paragraph.getActId());
			ps.setString(2, paragraph.getHeader());
			Clob fullTextClob = getDbService().openConnection().createClob();
			fullTextClob.setString(1, paragraph.getFullText());
			ps.setClob(3, fullTextClob);
			Clob absTextClob = getDbService().openConnection().createClob();
			absTextClob.setString(1, paragraph.getAbstractText());
			ps.setClob(4, absTextClob);
			newSectionId = ps.executeUpdate();
			logger.info("newSectionId check:" + newSectionId);
			if (newSectionId != Constants.Status.INITIAL.getStatus()) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						newSectionId = keys.getInt(1);
					}
				}
			}
			logger.info("newSectionId check:" + newSectionId);
			logger.info("New Section Insert Status: "
					+ (newSectionId == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info(
					"SQLException while creating new paragraph. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return newSectionId;
	}

	@Override
	public Paragraph get(int paraId) {
		Paragraph para = null;
		String query = Config.getProperty("Paragraph.find");
		logger.info("Executing query[" + query + "], paraId[" + paraId + "]");
		try {
			PreparedStatement statement = getDbService().openConnection()
					.prepareStatement(query);
			statement.setInt(1, paraId);
			ResultSet rs = statement.executeQuery();
			if (rs != null && rs.isBeforeFirst()) {
				rs.next();

				para = new Paragraph();
				para.setId(paraId);
				para.setFullText(rs.getString("fText"));
				para.setAbstractText(rs.getString("absText"));

			} else {
				logger.info("No Paragraph returned in ResultSet for paraId["
						+ paraId + "].");
			}
		} catch (SQLException e) {
			logger.info("SQLException while retrievig Paragraphs. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return para;
	}

	// @Override
	// public int deleteParagraph(Paragraph paragraph) {
	// return 0;
	// }

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public int updateParagraphContent(Paragraph para) {
		int status = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Paragraph.updateContent");
		logger.info("Executing Query[" + query + "], Parameters[" + para + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);

			Clob fullTextClob = getDbService().openConnection().createClob();
			fullTextClob.setString(1, para.getFullText());
			ps.setClob(1, fullTextClob);
			ps.setInt(2, para.getId());
			status = ps.executeUpdate();
			status = status == Status.INITIAL.getStatus() ? Status.FAILURE
					.getStatus() : Status.SUCCESS.getStatus();
			logger.info("Section content Update Status: "
					+ (status == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info(
					"SQLException while updating paragraph content. Message: {}",
					e.getMessage());
			status = Status.FAILURE.getStatus();
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int updateTranslation(Paragraph para) {
		int status = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Paragraph.updateTranslation");
		logger.info("Executing Query[" + query + "], Parameters[" + para + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);

			Clob absTextClob = getDbService().openConnection().createClob();
			absTextClob.setString(1, para.getAbstractText());
			ps.setClob(1, absTextClob);
			ps.setInt(2, para.getId());
			status = ps.executeUpdate();
			status = status == Status.INITIAL.getStatus() ? Status.FAILURE
					.getStatus() : Status.SUCCESS.getStatus();
			logger.info("Section Translation Update Status: "
					+ (status == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info(
					"SQLException while updating paragraph translation. Message: {}",
					e.getMessage());
			status = Status.FAILURE.getStatus();
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

}
