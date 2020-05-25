package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginNameExistsException extends ResponseStatusException {
	public LoginNameExistsException() {
		this(HttpStatus.BAD_REQUEST, "Login name exists already");
	}

	private LoginNameExistsException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
