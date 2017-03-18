package in.co.jurist.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBService {

	private static final Logger logger = LoggerFactory
			.getLogger(DBService.class);

	// Spring injected
	private DataSource dataSource;

	private Connection connection;

	protected DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private DBService() {
		logger.info("DBService constructor called!");
	}

	public Connection openConnection() {
		if (dataSource != null) {
			if (connection == null) {
				try {
					connection = dataSource.getConnection();
				} catch (SQLException e) {
					logger.debug(
							"SQLException while getting Database connection. {}",
							e.getMessage());
				}
			}
			return connection;
		} else {
			logger.info("DataSource is not initialised.");
		}
		return null;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				logger.debug(
						"SQLException while closing Database connection. {}",
						e.getMessage());
			}
		}
	}

	public void rollback() {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				logger.debug("SQLException while doing rollback. {}",
						e.getMessage());
			}
		}
	}

	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			logger.debug("SQLException for commit. {}", e.getMessage());
		}
	}

}
