package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HashTagNotFoundException extends ResponseStatusException {
	public HashTagNotFoundException() {
		this(HttpStatus.NOT_FOUND, "HashTag could not be found");
	}

	private HashTagNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}