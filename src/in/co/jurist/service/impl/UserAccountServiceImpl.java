package in.co.jurist.service.impl;

import in.co.jurist.dao.UserAccountDao;
import in.co.jurist.model.UserAccount;
import in.co.jurist.service.UserAccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountServiceImpl implements UserAccountService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserAccountServiceImpl.class);

	// Spring injected
	private UserAccountDao userAccountDao;

	public UserAccountServiceImpl() {

	}

	public UserAccountDao getUserAccountDao() {
		return userAccountDao;
	}

	public void setUserAccountDao(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	@Override
	public int registerUser(UserAccount userAccount) {
		int status = isUserRegistered(userAccount);
		/* User Not registered */
		if (status == 0) {
			if (userAccount.getPassword() == null
					|| userAccount.getPassword().isEmpty()) {
				userAccount.setPassword("");
				logger.info("Password is NULL. Setting it to empty string.");
			}
			if (userAccount.getToken() == null
					|| userAccount.getToken().isEmpty()) {
				userAccount.setToken("");
				logger.info("Token is NULL. Setting it to empty string.");
			}
			status = getUserAccountDao().createUserAccount(userAccount);
		}
		return status;
	}

	@Override
	public int isUserRegistered(UserAccount userAccount) {
		return getUserAccountDao().isUserRegistered(userAccount);
	}

	@Override
	public int loginUser(UserAccount userAccount) {
		return getUserAccountDao().authenticate(userAccount);
	}

}
