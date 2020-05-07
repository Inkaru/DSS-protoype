package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Project {

	@Id
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonProperty
	@DBRef
	private List<User> creators;
	@JsonProperty
	@DBRef
	private List<User> participants;

	public Project name(String name) {
		this.name = name;
		return this;
	}

	public Project description(String description) {
		this.description = description;
		return this;
	}

	public Project creators(List<User> creators) {
		this.creators = creators;
		return this;
	}

	public Project likes(List<User> likes) {
		this.participants = likes;
		return this;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", creators=" + creators +
				", likes=" + participants +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Project project = (Project) o;
		return id.equals(project.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<User> getCreators() {
		return creators;
	}

	public List<User> getParticipants() {
		return participants;
	}
}