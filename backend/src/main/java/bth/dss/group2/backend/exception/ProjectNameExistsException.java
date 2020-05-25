package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProjectNameExistsException extends ResponseStatusException {
	public ProjectNameExistsException() {
		this(HttpStatus.BAD_REQUEST, "Project name exists already");
	}

	private ProjectNameExistsException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
