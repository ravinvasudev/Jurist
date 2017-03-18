package in.co.jurist.service;

import in.co.jurist.model.Amendment;

import java.util.List;

public interface AmendmentService {

	/**
	 * Returns filtered list of Amendment[id, header, paraId] from Pre-loaded
	 * amendments.
	 * 
	 * @param paraId
	 * @return list of amendments
	 */
	public List<Amendment> filterAmendments(int paraId);

	/**
	 * Adds new Amendment in Database and to the Pre-loaded Amendment as well.
	 * 
	 * @param amendment
	 * @return 1 if success, 2 if already exists, 3 if input is not valid, 0 if
	 *         failure
	 */
	public int addNewAmendment(Amendment amendment);

	/**
	 * Checks if Amendment Header & paraId matches with one of the Pre-loaded
	 * Amendments.
	 * 
	 * @param amendment
	 * @return true if exists, false otherwise
	 */
	public boolean isAmendmentExists(Amendment amendment);

	/**
	 * Returns Amendment[id, header, paraId] from Pre-loaded amendments.
	 * 
	 * @param amendmentId
	 * @return
	 */
	public Amendment getAmendment(int amendmentId);

	/**
	 * Returns total Amendments under specific Act.
	 * 
	 * @param Id
	 * @return number of amendments exists in Act
	 */
	public int getAmendmentCount(int paraId);
}
