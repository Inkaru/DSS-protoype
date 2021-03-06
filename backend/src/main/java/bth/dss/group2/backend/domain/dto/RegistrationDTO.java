package bth.dss.group2.backend.domain.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.validation.PasswordMatches;
import bth.dss.group2.backend.validation.ValidEmail;
import bth.dss.group2.backend.validation.ValidPassword;

@PasswordMatches
public class RegistrationDTO {

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
	private UserDTO.UserType type;

	public RegistrationDTO() {
	}

	public RegistrationDTO(String loginName, String email, String password, String passwordRepeat, UserDTO.UserType type) {
		this(loginName, email, password, passwordRepeat);
		this.type = type;
	}

	public RegistrationDTO(String loginName, String email, String password, String passwordRepeat) {
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

	public UserDTO.UserType getType() {
		return type;
	}

	public RegistrationDTO setType(UserDTO.UserType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return String.format(
				"Registration[loginName=%s, email='%s',password='%s', passwordRepeat='%s',type='%s']",
				loginName, email, password, passwordRepeat, type);
	}
}
