package in.co.jurist.service;

import in.co.jurist.model.UserAccount;

public interface UserAccountService {

	/** Returns userId if success, 0 otherwise. */
	public int registerUser(UserAccount userAccount);

	public int loginUser(UserAccount userAccount);

	/** Returns userId if registered, 0 otherwise. */
	public int isUserRegistered(UserAccount userAccount);

}
