package in.co.jurist.dao.impl;

import in.co.jurist.dao.ActDao;
import in.co.jurist.model.Act;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;
import in.co.jurist.util.Constants;
import in.co.jurist.util.Constants.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActDaoImpl implements ActDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ActDaoImpl.class);

	private DBService dbService;

	public ActDaoImpl() {
		logger.info("ActDaoImpl constructor called!");
	}

	@Override
	public List<Act> loadAll() {
		List<Act> actList = null;
		String query = Config.getProperty("Act.findAll");
		logger.info("Executing query[" + query + "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {
				actList = new ArrayList<Act>();
				while (rs.next()) {
					Act act = new Act();
					act.setId(rs.getInt("id"));
					act.setName(rs.getString("name"));
					act.setDescription(rs.getString("description"));
					Date announcedDate = rs.getDate("announcedDate");
					act.setAnnouncedDate(announcedDate);
					act.setNumber(rs.getString("number"));
					actList.add(act);
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
		return actList;
	}

	@Override
	public int createAct(Act act) {
		int newActId = Constants.Status.INITIAL.getStatus();
		String query = Config.getProperty("Act.new");
		logger.info("Executing Query[" + query + "], Parameters["
				+ act.toString() + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query,
							PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, act.getNumber());
			ps.setString(2, act.getName());
			ps.setString(3, act.getDescription());
			java.sql.Date sqlDate = new java.sql.Date(act.getAnnouncedDate()
					.getTime());
			ps.setDate(4, sqlDate);
			newActId = ps.executeUpdate();
			logger.info("newActId check:" + newActId);
			if (newActId != Constants.Status.INITIAL.getStatus()) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						newActId = keys.getInt(1);
					}
				}
			}
			logger.info("New Act Insert Status: "
					+ (newActId == Status.INITIAL.getStatus() ? "FAILURE"
							: "SUCCESS"));
		} catch (SQLException e) {
			logger.info("SQLException while creating new act. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return newActId;
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public int updateActInfo(Act act) {
		int status = Constants.Status.FAILURE.getStatus();
		String query = Config.getProperty("Act.updateActInfo");
		logger.info("Executing Query[" + query + "], Parameters["
				+ act.toString() + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, act.getNumber());
			ps.setString(2, act.getName());
			ps.setString(3, act.getDescription());
			java.sql.Date sqlDate = new java.sql.Date(act.getAnnouncedDate()
					.getTime());
			ps.setDate(4, sqlDate);
			ps.setInt(5, act.getId());
			status = ps.executeUpdate();
			logger.info("Act Info update Status: "
					+ (status == Status.SUCCESS.getStatus() ? "SUCCESS"
							: "FAILURE"));
		} catch (SQLException e) {
			logger.info("SQLException while updating act info. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

}
