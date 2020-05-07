package bth.dss.group2.backend.exception;

public class EmailNotFoundException extends Exception {
	public EmailNotFoundException() {
		this("Email address could not be found");
	}

	public EmailNotFoundException(final String message) {
		super(message);
	}
}
