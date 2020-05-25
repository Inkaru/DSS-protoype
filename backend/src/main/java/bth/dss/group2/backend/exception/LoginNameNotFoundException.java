package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginNameNotFoundException extends ResponseStatusException {
	public LoginNameNotFoundException() {
		this(HttpStatus.NOT_FOUND, "Login name not found");
	}

	private LoginNameNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
