package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ChatChannelNotFoundException extends ResponseStatusException {
	public ChatChannelNotFoundException() {
		this(HttpStatus.BAD_REQUEST, "Email address could not be found");
	}

	public ChatChannelNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
