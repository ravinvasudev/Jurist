package in.co.jurist.dao.impl;

import in.co.jurist.dao.CategoryDao;
import in.co.jurist.model.Category;
import in.co.jurist.service.DBService;
import in.co.jurist.util.Config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDaoImpl implements CategoryDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CategoryDaoImpl.class);

	// Spring injected
	private DBService dbService;

	public CategoryDaoImpl() {
		logger.info("CategoryDaoImpl constructor called!");
	}

	@Override
	public List<Category> loadAll() {
		List<Category> categoryList = null;
		String query = Config.getProperty("Category.findAll");
		logger.info("Executing query[" + query + "]");
		try {
			Statement statement = getDbService().openConnection()
					.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs != null && rs.isBeforeFirst()) {
				categoryList = new ArrayList<Category>();
				while (rs.next()) {
					Category category = new Category();
					category.setId(rs.getInt("id"));
					category.setName(rs.getString("name"));

					categoryList.add(category);
				}
			} else {
				logger.info("No Categories returned in ResultSet.");
			}
		} catch (SQLException e) {
			logger.info(
					"SQLException while retrieving Categories. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return categoryList;
	}

	@Override
	public int createCategory(Category category) {
		int status = 0;
		String query = Config.getProperty("Category.new");
		logger.info("Executing Query[" + query + "], Parameters[" + category + "]");
		try {
			PreparedStatement ps = getDbService().openConnection()
					.prepareStatement(query);

			ps.setString(1, category.getName());
			status = ps.executeUpdate();
			
			logger.info("Category Insert Status: " + (status == 1 ? "SUCCESS" : "FAILURE"));
			
		} catch (SQLException e) {
			logger.info(
					"SQLException while creating new category. Message: {}",
					e.getMessage());
		} finally {
			getDbService().closeConnection();
		}
		return status;
	}

//	@Override
//	public int updateCategory(Category category) {
//		return 0;
//	}
//
//	@Override
//	public int deleteCategory(Category category) {
//		return 0;
//	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

}
