package bth.dss.group2.backend.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Project")
public class Project {

	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
	private String description;
	@DBRef(lazy = true)
	private User creator;
	@DBRef(lazy = true)
	private Set<User> participants;
	@DBRef(lazy = true)
	private Set<Tag> tags;
	@DBRef(lazy = true)
	private Location location;

	public Project() {
		participants = new HashSet<>();
		tags = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public Project setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Project setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Project setDescription(String description) {
		this.description = description;
		return this;
	}

	public User getCreator() {
		return creator;
	}

	public Project setCreator(User creator) {
		this.creator = creator;
		return this;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public Project setParticipants(Set<User> participants) {
		this.participants = participants;
		return this;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Project setTags(Set<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public Location getLocation() {
		return location;
	}

	public Project setLocation(Location location) {
		this.location = location;
		return this;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", creator=" + creator +
				", participants=" + participants.stream().map(User::getLoginName).collect(Collectors.joining(",")) +
				", tags=" + tags.stream().map(Tag::getName).collect(Collectors.joining(",")) +
				", location=" + location +
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
