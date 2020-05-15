package bth.dss.group2.backend.model.dto;

public class ProjectDto {
	private String id;
	private String name;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectDto name(String name) {
		this.name = name;
		return this;
	}

	public ProjectDto description(String description) {
		this.description = description;
		return this;
	}
}
