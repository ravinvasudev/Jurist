package in.co.jurist.dao.impl;

import in.co.jurist.dao.AmendmentDao;
import in.co.jurist.model.Amendment;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmendmentDaoImpl implements AmendmentDao {

	private static final Logger logger = LoggerFactory
			.getLogger(AmendmentDaoImpl.class);

	private DBService dbService;

	public AmendmentDaoImpl() {
		logger.info("AmendmentDaoImpl constructor called!");
	}

	@Override
	public List<Amendment> loadAll() {
		List<Amendment> amendmentList = null;
		String query = Config.getProperty("Amendment.findAll");
		logger.info("Executing query[" + query + "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {
				amendmentList = new ArrayList<Amendment>();
				while (rs.next()) {

					Amendment amendment = new Amendment();

					amendment.setId(rs.getInt("id"));
					amendment.setHeader(rs.getString("header"));
					// amendment.setFullText(rs.getString("ftext"));
					// amendment.setAbstractText(rs.getString("abstext"));
					Date announcedDate = rs.getDate("announcedDate");
					amendment.setAnnouncedDate(announcedDate);
					amendment.setParagraphId(rs.getInt("paraId"));
					amendmentList.add(amendment);
				}
			} else {
				logger.info("No Amendment returned in ResultSet.");
			}
		} catch (SQLException e) {
			logger.info("SQLException while retrieving Amendment. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return amendmentList;
	}

	@Override
	public int createAmendment(Amendment amendment) {
		int status = 0;
		String query = Config.getProperty("Amendment.new");
		logger.info("Executing Query[" + query + "], Parameters[" + amendment
				+ "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setInt(1, amendment.getParagraphId());
			ps.setString(2, amendment.getHeader());

			Clob fullTextClob = getDbService().openConnection().createClob();
			fullTextClob.setString(1, amendment.getFullText());
			ps.setClob(3, fullTextClob);

			Clob absTextClob = getDbService().openConnection().createClob();
			absTextClob.setString(1, amendment.getAbstractText());
			ps.setClob(4, absTextClob);

			java.sql.Date sqlDate = new java.sql.Date(amendment
					.getAnnouncedDate().getTime());
			ps.setDate(5, sqlDate);

			status = ps.executeUpdate();

			logger.info("Amendment Insert Status: "
					+ (status == 1 ? "SUCCESS" : "FAILURE"));

		} catch (SQLException e) {
			logger.info(
					"SQLException while creating new amendment. Message: {}", e);
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public Amendment get(int amendmentId) {
		Amendment amendment = null;
		String query = Config.getProperty("Amendment.find");
		logger.info("Executing query[" + query + "], amendmentId[" + amendmentId
				+ "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {

				rs.next();
				amendment = new Amendment();
				amendment.setId(amendmentId);
				amendment.setFullText(rs.getString("ftext"));
				amendment.setAbstractText(rs.getString("abstext"));
			} else {
				logger.info("No Amendments returned in ResultSet for amendmentId["
						+ amendmentId + "]");
			}
		} catch (SQLException e) {
			logger.info("SQLException while retrievig Amendment. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return amendment;
	}

	// @Override
	// public int deleteAmendment(Amendment amendment) {
	// return 0;
	// }
	//
	// @Override
	// public int updateAmendment(Amendment amendment) {
	// return 0;
	// }

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

}
