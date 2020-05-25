package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProjectNotFoundException extends ResponseStatusException {
	public ProjectNotFoundException() {
		this(HttpStatus.NOT_FOUND, "Project could not be found");
	}

	private ProjectNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
