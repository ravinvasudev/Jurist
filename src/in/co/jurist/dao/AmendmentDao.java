package in.co.jurist.dao;

import in.co.jurist.model.Amendment;

import java.util.List;

public interface AmendmentDao {

	/**
	 * Pre-load all the Amendments header, paraId, amendmentId
	 * 
	 * @return list of Amendment
	 */
	public List<Amendment> loadAll();

	/** Create a new Amendment and save in database. */
	public int createAmendment(Amendment amendment);

	/** Get a Amendment full text, abstract text */
	public Amendment get(int amendmentId);

	// /** Update existing Amendment. */
	// public int updateAmendment(Amendment amendment);
	//
	// /** Delete an Amendment. */
	// public int deleteAmendment(Amendment amendment);
}
