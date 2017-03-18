package in.co.jurist.service;

import in.co.jurist.model.Keyword;
import in.co.jurist.model.TagItem;

import java.util.List;

public interface KeywordService {

	public List<Keyword> getAll();

	public List<Keyword> filterKeywords(int actId, int paraId, int amendmentId);

	/**
	 * Adds New Keyword or Keyword Text for existing Keyword in Database and to
	 * the Pre-loaded Keywords as well.
	 * 
	 * @param keyword
	 * @return if success, returns newly created Keyword ID field for Add
	 *         operation or Keyword ID for Update operation, -1 if error, -2 if
	 *         already exists, -3 if input is not valid, 0 if failure
	 */
	public int addNewKeyword(Keyword keyword);

	/** Compares ACT ID, PARA ID, AMENDMENT ID for match with new Keyword. */
	public boolean isKeywordExist(Keyword keyword);

	/** Returns Previously searched Keyword IDs for similar search text. */
	public List<Integer> getResultKeywordMapping(String text);

	/*
	 * Adds a Keyword/Search-Result mapping [Keyword ID, Search Text] for next
	 * time search.
	 */
	public void addResultKeywordMapping(List<Integer> keywordIdList, String text);

	/** Delete Keyword text. */
	public int deleteKeyworText(List<TagItem> tagItems);

}
