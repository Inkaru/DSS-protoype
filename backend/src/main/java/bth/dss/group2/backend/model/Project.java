package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Project {

	@Id
	private String id;
	@Indexed(unique = true)
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonProperty
	@DBRef
	private List<AbstractUser> creators;
	@JsonProperty
	@DBRef
	private List<AbstractUser> participants;

	public Project name(String name) {
		this.name = name;
		return this;
	}

	public String getId() {
		return id;
	}

	public Project description(String description) {
		this.description = description;
		return this;
	}

	public Project creators(List<AbstractUser> creators) {
		this.creators = creators;
		return this;
	}

	public Project likes(List<AbstractUser> likes) {
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

	public List<AbstractUser> getCreators() {
		return creators;
	}

	public List<AbstractUser> getParticipants() {
		return participants;
	}
}
