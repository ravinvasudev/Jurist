package in.co.jurist.action;

import in.co.jurist.model.UserAccount;
import in.co.jurist.service.UserAccountService;
import in.co.jurist.util.Constants.Attribute;
import in.co.jurist.util.Constants.InitParam;
import in.co.jurist.util.Constants.InitValue;
import in.co.jurist.util.Constants.Status;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class UserAccountAction extends ActionSupport implements
		ServletRequestAware, ServletContextAware, ParameterAware {

	private static final Logger logger = LoggerFactory
			.getLogger(UserAccountAction.class);

	private static final long serialVersionUID = 1L;

	/* Spring injected */
	private UserAccountService userAccountService;

	private HttpServletRequest servletRequest;

	private ServletContext servletContext;

	private UserAccount user;

	private String displayName;

	/* Output parameter in response to Register User. */
	private int status;

	public UserAccountAction() {
		
	}

	public String validateUser() {
		int status = getUserAccountService().isUserRegistered(getUser());
		setStatus(status);
		if (status == 1) {
			return SUCCESS;
		} else {
			return NONE;
		}
	}

	/** Returns userId if registered, 0 otherwise. */
	public String registerUser() {
		setStatus(getUserAccountService().registerUser(getUser()));
		setUser(null);
		return SUCCESS;
	}

	public String loginUser() {
		int status = getUserAccountService().loginUser(getUser());
		getUser().setPassword("");
		setStatus(status);
		if (status == Status.SUCCESS.getStatus()) {
			HttpSession httpSession = getServletRequest().getSession();
			httpSession.setAttribute(Attribute.USERNAME.get(), getUser()
					.getEmail());
			httpSession.setMaxInactiveInterval(getSessionTimeout());
			return SUCCESS;
		} else {
			return NONE;
		}
	}

	public String logoutUser() {
		setStatus(Status.SUCCESS.getStatus());
		return SUCCESS;
	}

	private int getSessionTimeout() {
		String configSessionTimeout = getServletContext().getInitParameter(
				InitParam.SESSION_TIMEOUT.get());
		int sessionTimeout = InitValue.ZERO.get();
		try {
			sessionTimeout = Integer.parseInt(configSessionTimeout);
		} catch (NumberFormatException ex) {

		}
		return (sessionTimeout == InitValue.ZERO.get() ? InitValue.SESSION_TIMEOUT
				.get() : sessionTimeout);
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserAccountService getUserAccountService() {
		return userAccountService;
	}

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.servletRequest;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		Iterator<Entry<String, String[]>> it = parameters.entrySet().iterator();
		if (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			String[] val = entry.getValue();
			for (String str : val) {
				logger.info(String.format("Key[{}] - Value[{}]", key, str));
				logger.info(String.format("Key[%s] - Value[%s]", key, str));
			}
		}
	}
}
