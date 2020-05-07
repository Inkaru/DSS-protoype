package bth.dss.group2.backend.expections;

public class UserNotFoundException extends Exception {
	public UserNotFoundException() {
		this("User could not be found");
	}

	public UserNotFoundException(final String message) {
		super(message);
	}
}
