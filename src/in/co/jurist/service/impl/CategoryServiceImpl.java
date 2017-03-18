package in.co.jurist.service.impl;

import in.co.jurist.dao.CategoryDao;
import in.co.jurist.model.Category;
import in.co.jurist.service.CategoryService;
import in.co.jurist.util.Constants.Status;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory
			.getLogger(CategoryServiceImpl.class);

	/* Spring injected */
	private CategoryDao categoryDao;

	/* Auto loaded Categories. */
	private List<Category> list;

	public CategoryServiceImpl() {
		// logger.info("CategoryService constructor called!");
	}

	@PostConstruct
	private void init() {
		// logger.info("Category Post construct called.");
		setList(getCategoryDao().loadAll());
	}

	@Override
	public int addNewCategory(Category category) {
		int status = 0;
		if (!isCategoryExists(category)) {
			status = getCategoryDao().createCategory(category);
			if (status == Status.SUCCESS.getStatus()) {
				addToPreloadedList(category);
			} else {
				logger.info("Unable to add Category.");
			}
		} else {
			logger.info("Category: " + category.getName() + " alreay exists.");
			status = Status.RECORD_EXISTS.getStatus();
		}
		return status;
	}

	/**
	 * Add a Category to Preloaded <code>List<code><</code>Category
	 * <code>></code></code>
	 */
	private void addToPreloadedList(Category category) {
		if (this.list == null) {
			this.list = new ArrayList<Category>();
		}
		Category cat = new Category();
		cat.setId(getList().size() + 1);
		cat.setName(category.getName());

		getList().add(cat);
	}

	@Override
	public boolean isCategoryExists(Category category) {
		if (getList() != null && !getList().isEmpty()) {
			for (Category cat : getList()) {
				if (cat.getName().trim()
						.equalsIgnoreCase(category.getName().trim())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Category> getCategories() {
		return getList();
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
		if (list != null && !list.isEmpty()) {
			/* Remove in deployment. */
			for (Category cat : getList()) {
				logger.info(cat.toString());
			}
		} else {
			logger.info("CategoryService: List is null.");
		}
	}

}