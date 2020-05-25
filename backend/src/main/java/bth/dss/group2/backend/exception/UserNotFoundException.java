package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
	public UserNotFoundException() {
		this(HttpStatus.NOT_FOUND, "User could not be found");
	}

	private UserNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
