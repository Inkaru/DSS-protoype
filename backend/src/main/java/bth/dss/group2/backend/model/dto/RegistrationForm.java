package bth.dss.group2.backend.model.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.validation.PasswordMatches;
import bth.dss.group2.backend.validation.ValidEmail;
import bth.dss.group2.backend.validation.ValidPassword;

@PasswordMatches
public class RegistrationForm {

	@NotNull
	private String loginName;
	@NotNull
	@ValidEmail(message = "The email address is invalid")
	private String email;
	@NotNull
	@ValidPassword(message = "The password should have at least 1 Uppercase letter, 1 lowercase letter, 1 digit and 1 symbol")
	private String password;
	@NotNull
	private String passwordRepeat;

	public RegistrationForm(String loginName, String email, String password, String passwordRepeat) {
		this.loginName = loginName;
		this.email = email;
		this.password = password;
		this.passwordRepeat = passwordRepeat;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
				"Registration[loginName=%s, email='%s',password='%s', passwordRepeat='%s']",
				loginName, email, password, passwordRepeat);
	}
}
