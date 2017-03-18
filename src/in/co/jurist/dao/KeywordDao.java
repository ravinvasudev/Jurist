package in.co.jurist.dao;

import in.co.jurist.model.Keyword;

import java.util.List;

public interface KeywordDao {

	/** Preload all the Keywords. */
	public List<Keyword> loadAll();

	/** Create a new Keyword. */
	public int createKeyword(Keyword keyword);

	/** Add keyword Text to existing Keyword. */
	public int addKeywordText(Keyword keyword);
	
	/** Delete Keyword text. */
	public int deleteKeyworText(int id, String keywordTextToUpdate);
	
	public List<String> getResultKeywordMapping(String text);

	public void addResultKeywordMapping(String mapping, String text);

}
