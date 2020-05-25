package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailNotFoundException extends ResponseStatusException {
	public EmailNotFoundException() {
		this(HttpStatus.NOT_FOUND, "Email address could not be found");
	}

	private EmailNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
