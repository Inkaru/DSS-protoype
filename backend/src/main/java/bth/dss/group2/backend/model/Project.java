package bth.dss.group2.backend.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Project")
public class Project {

	@Id
	private String id;
	@Indexed(unique = true)
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonProperty
	@DBRef(lazy = true)
	private User creator;
	@JsonProperty
	@DBRef(lazy = true)
	private Set<User> participants;
	@DBRef(lazy = true)
	private Set<User> likes;
	@JsonProperty
	@DBRef(lazy = true)
	private Set<User> follows;

	public Project() {
		participants = new HashSet<>();
		likes = new HashSet<>();
		follows = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public User getCreator() {
		return creator;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public Project name(String name) {
		this.name = name;
		return this;
	}

	public Project description(String description) {
		this.description = description;
		return this;
	}

	public Project creator(User creator) {
		this.creator = creator;
		return this;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", creators=" + creator +
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
}
