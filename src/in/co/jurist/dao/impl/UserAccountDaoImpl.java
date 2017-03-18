package in.co.jurist.dao.impl;

import in.co.jurist.dao.UserAccountDao;
import in.co.jurist.model.UserAccount;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountDaoImpl implements UserAccountDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserAccountDaoImpl.class);

	private DBService dbService;

	public UserAccountDaoImpl() {
		logger.info("UserAccountDaoImpl constructor called!");
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public int createUserAccount(UserAccount user) {
		int status = 0;
		String query = Config.getProperty("User.createUser");
		logger.info("Executing Query[" + query + "], Parameters[" + user + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getDisplayName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getToken());
			ps.setString(6, user.getAccountType());

			status = ps.executeUpdate();
			if (status != 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						status = keys.getInt(1);
					}
				}
			}
			logger.info("User Account Creation Insert Status: "
					+ (status == 0 ? "FAILURE" : "SUCCESS"));
		} catch (SQLException e) {
			logger.info("Exception while executing query: {} Message: {}",
					query, e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int authenticate(UserAccount user) {
		int status = 0;
		String query = Config.getProperty("User.authenticate");
		logger.info("Executing Query[" + query + "], Parameters["
				+ user.getEmail() + ", " + user.getPassword() + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());

			ResultSet rs = ps.executeQuery();

			if (rs != null && rs.isBeforeFirst()) {
				logger.info("Username authenticated!");
				status = 1;
				rs.next();

				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setDisplayName(rs.getString("display_name"));
				//user.setAccountType(rs.getString("account_type"));
			} else {
				logger.info("User: {} does not exists.", user);
			}

		} catch (SQLException e) {
			logger.info("Exception while executing query: {} Message: {}",
					query, e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

	@Override
	public int isUserRegistered(UserAccount user) {
		int status = 0;
		String query = Config.getProperty("User.isUserRegistered");
		logger.info("Executing Query[" + query + "], Parameters["
				+ user.getEmail() + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);
			ps.setString(1, user.getEmail());

			ResultSet rs = ps.executeQuery();

			if (rs != null && rs.isBeforeFirst()) {
				rs.next();
				String accountType = rs.getString("account_type");
				if (accountType.equalsIgnoreCase(user.getAccountType())) {
					logger.info("User Exists!");
					status = 1;
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setDisplayName(rs.getString("display_name"));
				} else {
					logger.info("User exists with different provider!");
					status = 2;
				}
			} else {
				logger.info("User: {} does not exists.", user.getEmail());
			}

		} catch (SQLException e) {
			logger.info("Exception while executing query: {} Message: {}",
					query, e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

}
