package bth.dss.group2.backend.expections;

public class LoginNameExistsException extends Exception {
	public LoginNameExistsException() {
		this("Login name exists already");
	}

	public LoginNameExistsException(final String message) {
		super(message);
	}
}
