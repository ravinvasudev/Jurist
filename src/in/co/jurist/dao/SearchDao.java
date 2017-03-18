package in.co.jurist.dao;

import in.co.jurist.model.Keyword;

import java.util.List;

public interface SearchDao {

	/** Get all the Keywords. */
	public List<Keyword> getKeywords();

}
