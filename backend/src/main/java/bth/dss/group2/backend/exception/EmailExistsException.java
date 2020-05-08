package bth.dss.group2.backend.exception;

public class EmailExistsException extends Exception {
	public EmailExistsException() {
		this("Email address exists already");
	}

	public EmailExistsException(final String message) {
		super(message);
	}
}
