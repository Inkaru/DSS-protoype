package bth.dss.group2.backend.model.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.validation.PasswordMatches;
import bth.dss.group2.backend.validation.ValidEmail;
import bth.dss.group2.backend.validation.ValidPassword;

@PasswordMatches
public class Registration {

	@NotNull
	private String loginName;
	@NotNull
	@ValidEmail(message = "The email adress is invalid")
	private String emailAddress;
	@NotNull
	@ValidPassword(message = "The password should have at least 1 Uppercase letter, 1 lowercase letter, 1 digit and 1 symbol")
	private String password;
	@NotNull
	private String passwordRepeat;

	public Registration(String loginName, String emailAddress, String password, String passwordRepeat) {
		this.loginName = loginName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.passwordRepeat = passwordRepeat;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	@Override
	public String toString() {
		return String.format(
				"Registration[loginName=%s, emailAddress='%s',password='%s', passwordRepeat='%s']",
				loginName, emailAddress, password, passwordRepeat);
	}
}
