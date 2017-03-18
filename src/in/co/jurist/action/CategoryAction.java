package in.co.jurist.action;

import in.co.jurist.model.Category;
import in.co.jurist.service.CategoryService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class CategoryAction extends ActionSupport {

	private static final Logger logger = LoggerFactory
			.getLogger(CategoryAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private CategoryService categoryService;

	/* Input parameters. */
	private Category category;

	/* Output parameters in response to Load All Categories. */
	private List<Category> list;

	/* Output parameter in response to Add New Category */
	private int status;

	public CategoryAction() {
		// logger.info("CategoryAction constructor called.");
	}

	/* Get Pre-Loaded Categories. */
	public String getCategories() {
		setList(getCategoryService().getCategories());
		return SUCCESS;
	}

	/* Add a New Category in Database. */
	public String addCategory() {
		setStatus(getCategoryService().addNewCategory(getCategory()));
		return SUCCESS;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
		if (list == null || list.isEmpty()) {
			logger.info("CategoryAction: List is null.");
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}