package bth.dss.group2.backend.exception;

public class LoginNameNotFoundException extends Exception {
	public LoginNameNotFoundException() {
		this("Login name not found");
	}

	public LoginNameNotFoundException(final String message) {
		super(message);
	}
}