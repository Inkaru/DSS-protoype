package bth.dss.group2.backend.domain.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bth.dss.group2.backend.domain.HashTag;
import bth.dss.group2.backend.domain.Project;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.util.Util;

public class ProjectDTO {
	private String id;
	private String name;
	private String description;
	private UserDTO creator;
	private LocationDTO location;
	private Set<UserDTO> participants;
	private Set<UserDTO> follows;
	private Set<UserDTO> likes;
	private Set<String> hashTags;

	public ProjectDTO() {
		participants = new HashSet<>();
		follows = new HashSet<>();
		likes = new HashSet<>();
		hashTags = new HashSet<>();
		location = new LocationDTO();
	}

	public static ProjectDTO create(Project project) {
		ProjectDTO dto = new ProjectDTO();
		dto.setId(project.getId());
		dto.setDescription(project.getDescription());
		dto.setName(project.getName());
		dto.setHashTags(project.getHashTags().stream().map(HashTag::getName).collect(Collectors.toSet()));
		dto.setLocation(Util.getMapper().map(project.getLocation(), LocationDTO.class));
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

	public String getId() {
		return id;
	}

	public ProjectDTO setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ProjectDTO setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ProjectDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public ProjectDTO setCreator(UserDTO creator) {
		this.creator = creator;
		return this;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public ProjectDTO setLocation(LocationDTO location) {
		this.location = location;
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
}
