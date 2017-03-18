package in.co.jurist.dao;

import in.co.jurist.model.Category;

import java.util.List;

public interface CategoryDao {

	/** Get all the Categories from DB. */
	public List<Category> loadAll();

	/** Create a new Category */
	public int createCategory(Category category);

	// /** Update an existing Category. */
	// public int updateCategory(Category category);
	//
	// /** Delete Category. */
	// public int deleteCategory(Category category);
}
