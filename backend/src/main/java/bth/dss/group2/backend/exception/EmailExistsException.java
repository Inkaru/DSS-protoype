package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailExistsException extends ResponseStatusException {
	public EmailExistsException() {
		this(HttpStatus.BAD_REQUEST, "Email address exists already");
	}

	private EmailExistsException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
