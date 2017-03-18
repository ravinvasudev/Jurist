package in.co.jurist.service;

import in.co.jurist.model.Paragraph;

import java.util.List;

/**
 * @author Ravin
 *
 */
public interface ParagraphService {

	/**
	 * Returns filtered list of Paragraph[id, header, actId] from Pre-loaded
	 * paragraphs.
	 * 
	 * @param actId
	 * @return list of paragraphs
	 */
	public List<Paragraph> filterParagraphHeaders(int actId);

	/**
	 * Adds new Paragraph in Database and to the Pre-loaded Paragraphs as well.
	 * 
	 * @param paragraph
	 * @return Newly created Section ID field if success, -1 if error, -2 if
	 *         already exists, -3 if input is not valid, 0 if failure
	 */
	public int addNewParagraph(Paragraph paragraph);

	/**
	 * Checks if Paragraph Header & ActId matches with one of the Pre-loaded
	 * Paragraphs.
	 * 
	 * @param paragraph
	 * @return true if exists, false otherwise
	 */
	public boolean isParagraphExists(Paragraph paragraph);

	/**
	 * Returns Paragraph[id, header, actId] from Pre-loaded paragraphs.
	 * 
	 * @param paraId
	 * @return
	 */
	public Paragraph getParagraph(int paraId);

	/**
	 * Returns total sectons under specific Act.
	 * 
	 * @param actId
	 * @return number of paragraphs exists in Act
	 */
	public int getParagraphCount(int actId);

	/**
	 * Update Paragraph FullText.
	 * 
	 * @param reqPara
	 * @return 1 if success, 0 for failure, -3 if input is missing
	 */
	public int updateParagraphContent(Paragraph reqPara);

	/**
	 * Update Paragraph Translation.
	 * 
	 * @param reqPara
	 * @return 1 if success, 0 for failure, -3 if input is missing
	 */
	public int updateTranslation(Paragraph reqPara);

}
