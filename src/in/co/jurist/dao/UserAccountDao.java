package in.co.jurist.dao;

import in.co.jurist.model.UserAccount;

public interface UserAccountDao {

	/** Returns userId if success, 0 otherwise. */
	public int createUserAccount(UserAccount user);

	public int authenticate(UserAccount user);

	/** Returns userId if registered, 0 otherwise. */
	public int isUserRegistered(UserAccount userAccount);
}
