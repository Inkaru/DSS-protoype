package bth.dss.group2.backend.domain.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bth.dss.group2.backend.domain.Company;
import bth.dss.group2.backend.domain.Institution;
import bth.dss.group2.backend.domain.Person;
import bth.dss.group2.backend.domain.Project;
import bth.dss.group2.backend.domain.Tag;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.util.Util;

public class UserDTO {

	private String id;
	private String displayName;
	private String loginName;
	private String description;
	private String email;
	private String phoneNumber;
	private LocationDTO location;

	private Set<ProjectDTO> followedProjects;
	private Set<ProjectDTO> likedProjects;
	private Set<ProjectDTO> createdProjects;
	private Set<ProjectDTO> participatedProjects;
	private Set<MarketplaceItemDTO> marketplaceItems;
	private Set<String> fieldOfActivityTags;

	//Specific to Person:
	private String firstName;
	private String lastName;
	private String title;

	//Deciding which object
	private UserType type;

	public UserDTO() {
		followedProjects = new HashSet<>();
		likedProjects = new HashSet<>();
		createdProjects = new HashSet<>();
		participatedProjects = new HashSet<>();
		marketplaceItems = new HashSet<>();
		location = new LocationDTO();
		fieldOfActivityTags = new HashSet<>();
	}

	public static UserDTO create(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setLoginName(user.getLoginName());
		dto.setDisplayName(user.getDisplayName());
		dto.setDescription(user.getDescription());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		if (user.getLocation() != null) dto.setLocation(Util.getMapper().map(user.getLocation(), LocationDTO.class));
		if (user instanceof Person) {
			Person person = (Person) user;
			dto.setType(UserType.PERSON);
			dto.setFirstName(person.getFirstName());
			dto.setLastName(person.getLastName());
			dto.setTitle(person.getTitle());
		}
		else if (user instanceof Company) {
			dto.setType(UserType.COMPANY);
			//dto.setName(((Company) user).getName());
		}
		else if (user instanceof Institution) {
			dto.setType(UserType.INSTITUTION);
			//dto.setName(((Institution) user).getName());
		}
		dto.setFieldOfActivityTags(user.getFieldOfActivityTags()
				.stream()
				.map(Tag::getName)
				.collect(Collectors.toSet()));
		return dto;
	}

	public String getId() {
		return id;
	}

	public UserDTO setId(String id) {
		this.id = id;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public UserDTO setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public UserDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserDTO setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public UserDTO setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public static UserDTO createWithReferences(User user, List<Project> createdProjects, List<Project> participatedProjects) {
		UserDTO dto = create(user);
		dto.setCreatedProjects(createdProjects.stream().map(ProjectDTO::create).collect(Collectors.toSet()));
		dto.setParticipatedProjects(participatedProjects
				.stream()
				.map(ProjectDTO::create)
				.collect(Collectors.toSet()));
		dto.setFollowedProjects(user.getFollowedProjects()
				.stream()
				.map(ProjectDTO::create)
				.collect(Collectors.toSet()));
		dto.setLikedProjects(user.getLikedProjects().stream().map(ProjectDTO::create).collect(Collectors.toSet()));
		dto.setMarketplaceItems(user.getMarketplaceItems()
				.stream()
				.map(MarketplaceItemDTO::create)
				.collect(Collectors.toSet()));
		return dto;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public Set<ProjectDTO> getFollowedProjects() {
		return followedProjects;
	}

	public UserDTO setFollowedProjects(Set<ProjectDTO> followedProjects) {
		this.followedProjects = followedProjects;
		return this;
	}

	public Set<ProjectDTO> getLikedProjects() {
		return likedProjects;
	}

	public UserDTO setLikedProjects(Set<ProjectDTO> likedProjects) {
		this.likedProjects = likedProjects;
		return this;
	}

	public Set<ProjectDTO> getCreatedProjects() {
		return createdProjects;
	}

	public UserDTO setCreatedProjects(Set<ProjectDTO> createdProjects) {
		this.createdProjects = createdProjects;
		return this;
	}

	public Set<ProjectDTO> getParticipatedProjects() {
		return participatedProjects;
	}

	public UserDTO setParticipatedProjects(Set<ProjectDTO> participatedProjects) {
		this.participatedProjects = participatedProjects;
		return this;
	}

	public Set<MarketplaceItemDTO> getMarketplaceItems() {
		return marketplaceItems;
	}

	public UserDTO setMarketplaceItems(Set<MarketplaceItemDTO> marketplaceItems) {
		this.marketplaceItems = marketplaceItems;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public UserDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public UserType getType() {
		return type;
	}

	public UserDTO setType(UserType type) {
		this.type = type;
		return this;
	}

	public UserDTO setLocation(LocationDTO location) {
		this.location = location;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public UserDTO setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public Set<String> getFieldOfActivityTags() {
		return fieldOfActivityTags;
	}

	public UserDTO setFieldOfActivityTags(Set<String> fieldOfActivityTags) {
		this.fieldOfActivityTags = fieldOfActivityTags;
		return this;
	}

	public enum UserType {
		PERSON, COMPANY, INSTITUTION
	}
}
