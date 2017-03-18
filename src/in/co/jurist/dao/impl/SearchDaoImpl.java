package in.co.jurist.dao.impl;

import in.co.jurist.dao.SearchDao;
import in.co.jurist.model.Keyword;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchDaoImpl implements SearchDao {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchDaoImpl.class);

	private DBService dbService;

	public SearchDaoImpl() {
		logger.info("SearchDaoImpl constructor called!");
	}

	@Override
	public List<Keyword> getKeywords() {
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

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

}
