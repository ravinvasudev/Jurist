package in.co.jurist.service;

import in.co.jurist.model.SearchResult;

import java.util.List;


public interface SearchService {
	
	public List<SearchResult> find(String text);

}
