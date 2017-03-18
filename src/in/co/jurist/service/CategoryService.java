package in.co.jurist.service;

import in.co.jurist.model.Category;

import java.util.List;

public interface CategoryService {

	/**
	 * Method returns a preloaded <code>List<code><</code>Category<code>></code>
	 * </code>
	 */
	public List<Category> getCategories();

	/** Adds a new <code>Category</code> */
	public int addNewCategory(Category category);

	/**
	 * Check if <code>Category</code> exists in preloaded
	 * <code>List<code><</code>Category<code>></code></code>
	 */
	public boolean isCategoryExists(Category category);

}
