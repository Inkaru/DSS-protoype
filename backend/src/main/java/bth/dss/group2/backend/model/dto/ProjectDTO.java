package bth.dss.group2.backend.model.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bth.dss.group2.backend.model.HashTag;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;

public class ProjectDTO {
	private String id;
	private String name;
	private String description;
	private UserDTO creator;
	private Set<UserDTO> participants;
	private Set<UserDTO> follows;
	private Set<UserDTO> likes;
	private Set<String> hashTags;

	private ProjectDTO() {
		participants = new HashSet<>();
		follows = new HashSet<>();
		likes = new HashSet<>();
		hashTags = new HashSet<>();
	}

	public static ProjectDTO create(Project project) {
		ProjectDTO dto = new ProjectDTO();
		dto.setId(project.getId());
		dto.setDescription(project.getDescription());
		dto.setName(project.getName());
		dto.setHashTags(project.getHashTags().stream().map(HashTag::getName).collect(Collectors.toSet()));
		return dto;
	}

	public static ProjectDTO createWithReferences(Project project, List<User> follows, List<User> likes) {
		ProjectDTO dto = create(project);
		dto.setCreator(UserDTO.create(project.getCreator()));
		dto.setParticipants(project.getParticipants().stream().map(UserDTO::create).collect(Collectors.toSet()));
		dto.setFollows(follows.stream().map(UserDTO::create).collect(Collectors.toSet()));
		dto.setLikes(likes.stream().map(UserDTO::create).collect(Collectors.toSet()));
		return dto;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public ProjectDTO setCreator(UserDTO creator) {
		this.creator = creator;
		return this;
	}

	public Set<UserDTO> getParticipants() {
		return participants;
	}

	public ProjectDTO setParticipants(Set<UserDTO> participants) {
		this.participants = participants;
		return this;
	}

	public Set<UserDTO> getFollows() {
		return follows;
	}

	public ProjectDTO setFollows(Set<UserDTO> follows) {
		this.follows = follows;
		return this;
	}

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

	public Set<UserDTO> getLikes() {
		return likes;
	}

	public ProjectDTO setLikes(Set<UserDTO> likes) {
		this.likes = likes;
		return this;
	}

	public Set<String> getHashTags() {
		return hashTags;
	}

	public ProjectDTO setHashTags(Set<String> hashTags) {
		this.hashTags = hashTags;
		return this;
	}

	public ProjectDTO name(String name) {
		this.name = name;
		return this;
	}

	public ProjectDTO description(String description) {
		this.description = description;
		return this;
	}
}
