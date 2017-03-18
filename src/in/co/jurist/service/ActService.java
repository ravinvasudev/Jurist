package in.co.jurist.service;

import in.co.jurist.model.Act;

import java.util.List;

public interface ActService {

	/**
	 * Adds new Act in Database and to the Pre-loaded Acts as well.
	 * 
	 * @param act
	 * @return Newly created Act ID field if success, -1 if error, -2 if already
	 *         exists, -3 if input is not valid, 0 if failure
	 */
	public int addNewAct(Act act);

	/**
	 * Checks if Act Name & CategoryId matches with one of the Pre-loaded Acts.
	 * 
	 * @param act
	 * @return true if exists, false otherwise
	 */
	public boolean isActExists(Act act);

	/**
	 * Checks if Act Exists based on Act ID
	 * 
	 * @param actId
	 * @return true if exists, false otherwise
	 */
	public boolean isActExists(int actId);

	/**
	 * Get a specific Act
	 * 
	 * @param actId
	 * @return Act
	 */
	public Act getAct(int actId);

	/**
	 * Get All Acts
	 * 
	 * @return Acts
	 */
	public List<Act> getAllActs();

	/**
	 * Returns the Act Name.
	 * 
	 * @param actId
	 * @return name
	 */
	public String getActName(int actId);

	/**
	 * Update Act Info.
	 * 
	 * @param act
	 * @return 1 if success, 0 if failure
	 */
	public int updateActInfo(Act reqAct);

}
