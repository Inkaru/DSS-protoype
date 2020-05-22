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
	@DBRef/*(db = "User")*/
	private Set<User> creators;
	@JsonProperty
	@DBRef/*(db = "User")*/
	private Set<User> participants;

	public Project() {
		creators = new HashSet<>();
		participants = new HashSet<>();
	}

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

	public Project creators(Set<User> creators) {
		this.creators = creators;
		return this;
	}

	public Project likes(Set<User> likes) {
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

	public Set<User> getCreators() {
		return creators;
	}

	public Set<User> getParticipants() {
		return participants;
	}
}
