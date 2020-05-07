package bth.dss.group2.backend.expections;

public class ProjectNameExistsException extends Exception {
	public ProjectNameExistsException() {
		this("Project name exists already");
	}

	public ProjectNameExistsException(final String message) {
		super(message);
	}
}
