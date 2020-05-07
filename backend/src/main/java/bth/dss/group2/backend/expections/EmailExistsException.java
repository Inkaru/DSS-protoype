package bth.dss.group2.backend.expections;

public class EmailExistsException extends Exception {
	public EmailExistsException() {
		this("Email address exists already");
	}

	public EmailExistsException(final String message) {
		super(message);
	}
}
