package in.co.jurist.action;

import in.co.jurist.model.SearchResult;
import in.co.jurist.service.SearchService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport {

	 private static final Logger logger = LoggerFactory.getLogger(SearchAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private SearchService searchService;

	/* Input parameter. */
	private String q;

	/* Output parameter. */
	private List<SearchResult> searchResults;

	/* Output parameter */
	private int status;

	public SearchAction() {
		logger.info("SearchAction constructor called.");
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String search() {
		setSearchResults(getSearchService().find(getQ()));
		return SUCCESS;
	}

	public List<SearchResult> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<SearchResult> searchResults) {
		this.searchResults = searchResults;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
