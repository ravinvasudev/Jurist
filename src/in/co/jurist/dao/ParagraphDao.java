package in.co.jurist.dao;

import in.co.jurist.model.Paragraph;

import java.util.List;

public interface ParagraphDao {

	/**
	 * Pre-load all the Paragraphs header, actId, paraId
	 * 
	 * @return list of Paragraph
	 */
	public List<Paragraph> loadParagraphHeaders();

	/**
	 * Create a new Paragraph and save in database.
	 * 
	 * @param paragraph
	 * @return Newly created Section ID if success, -1 if failure
	 */
	public int createParagraph(Paragraph paragraph);

	/** Get a Paragraph full text, abstract text */
	public Paragraph get(int paraId);

	/**
	 * Update Paragraph FullText.
	 * 
	 * @param para
	 * @return 1 if success, 0 for failure
	 */
	public int updateParagraphContent(Paragraph para);

	/**
	 * Update Paragraph Translation.
	 * 
	 * @param para
	 * @return 1 if success, 0 for failure
	 */
	public int updateTranslation(Paragraph para);

}
