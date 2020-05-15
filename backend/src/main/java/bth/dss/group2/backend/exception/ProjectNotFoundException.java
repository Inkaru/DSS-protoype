package bth.dss.group2.backend.exception;

public class ProjectNotFoundException extends Exception {
	public ProjectNotFoundException() {
		this("Project could not be found");
	}

	public ProjectNotFoundException(final String message) {
		super(message);
	}
}
