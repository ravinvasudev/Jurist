package in.co.jurist.model;

import java.io.Serializable;
import java.util.Date;

public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date creationDate;

	private String displayName;

	private String email;

	private int id;

	private String name;

	private String token;

	private String password;

	private String accountType;

	public UserAccount() {

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "UserAccount [creationDate=" + creationDate + ", displayName="
				+ displayName + ", email=" + email + ", id=" + id + ", name="
				+ name + ", token=" + token + ", password=" + password
				+ ", accountType=" + accountType + "]";
	}

}