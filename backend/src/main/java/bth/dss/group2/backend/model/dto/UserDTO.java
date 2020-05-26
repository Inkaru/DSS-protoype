package bth.dss.group2.backend.model.dto;

import java.util.Set;
import java.util.stream.Collectors;

import bth.dss.group2.backend.model.Company;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.User;

public class UserDTO {

	private String id;
	private String loginName;
	private String description;
	private String email;
	private String phoneNumber;
	private String city;
	private String country;

	private Set<ProjectDTO> followedProjects;
	private Set<ProjectDTO> likedProjects;
	private Set<ProjectDTO> createdProjects;
	private Set<ProjectDTO> participatedProjects;
	private Set<MarketplaceItemDTO> marketplaceItems;

	//Specific to Person:
	private String address;
	private String firstName;
	private String lastName;
	private String title;

	//Deciding which object
	private UserType type;

	public UserDTO() {
	}

	public static UserDTO create(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setLoginName(user.getLoginName());
		dto.setDescription(user.getDescription());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setCity(user.getCity());
		dto.setCountry(user.getCountry());
		if (user instanceof Person) {
			Person person = (Person) user;
			dto.setType(UserType.PERSON);
			dto.setFirstName(person.getFirstName());
			dto.setLastName(person.getLastName());
			dto.setTitle(person.getTitle());
			dto.setAddress(person.getAddress());
		}
		else if (user instanceof Company) {
			dto.setType(UserType.COMPANY);
			//TODO: fill
		}

		/*else if(user instanceof Institution) {
			dto.setType(UserType.INSTITUTION);
			//TODO: fill
		}*/
		return dto;
	}

	public static UserDTO createWithReferences(User user) {
		UserDTO dto = create(user);
		dto.setCreatedProjects(user.getCreatedProjects().stream().map(ProjectDTO::create).collect(Collectors.toSet()));
		dto.setParticipatedProjects(user.getParticipatedProjects()
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

	public String getCity() {
		return city;
	}

	public UserDTO setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public UserDTO setCountry(String country) {
		this.country = country;
		return this;
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

	public String getAddress() {
		return address;
	}

	public UserDTO setAddress(String address) {
		this.address = address;
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

	public enum UserType {
		PERSON, COMPANY, INSTITUTION
	}
}
