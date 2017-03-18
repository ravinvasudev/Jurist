package in.co.jurist.dao;

import in.co.jurist.model.Act;

import java.util.List;

public interface ActDao {

	/** Get all the Acts from DB. */
	public List<Act> loadAll();

	/**
	 * Create a new Act.
	 * 
	 * @param act
	 * @return Newly created Act ID if success, -1 if failure
	 */
	public int createAct(Act act);

	/**
	 * Update Act Info.
	 * 
	 * @param act
	 * @return 1 if success, 0 if failure
	 */
	public int updateActInfo(Act act);

}
