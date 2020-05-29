package bth.dss.group2.backend.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public abstract class User {
	@Id
	protected String id;
	@Indexed(unique = true)
	private String loginName;
	private String displayName;
	private String hashedPassword;
	private String description;
	@Indexed(unique = true)
	private String email;
	private String phoneNumber;
	@DBRef(lazy = true)
	private Location location;
	@DBRef(lazy = true)
	private Set<Project> followedProjects;
	@DBRef(lazy = true)
	private Set<Project> likedProjects;
	@DBRef(lazy = true)
	private Set<MarketplaceItem> marketplaceItems;
	@DBRef(lazy = true)
	private Set<Tag> fieldOfActivityTags;

	public User() {
		followedProjects = new HashSet<>();
		likedProjects = new HashSet<>();
		marketplaceItems = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public User setLoginName(String loginName) {
		this.loginName = loginName;
		return getThis();
	}

	public String getDisplayName() {
		return displayName;
	}

	public User setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public User setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return getThis();
	}

	public String getDescription() {
		return description;
	}

	public User setDescription(String description) {
		this.description = description;
		return getThis();
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return getThis();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public User setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return getThis();
	}

	public Location getLocation() {
		return location;
	}

	public User setLocation(Location location) {
		this.location = location;
		return getThis();
	}

	public User setFollowedProjects(Set<Project> followedProjects) {
		this.followedProjects = followedProjects;
		return getThis();
	}

	public User setLikedProjects(Set<Project> likedProjects) {
		this.likedProjects = likedProjects;
		return getThis();
	}

	public User setMarketplaceItems(Set<MarketplaceItem> marketplaceItems) {
		this.marketplaceItems = marketplaceItems;
		return getThis();
	}

	public Set<Project> getFollowedProjects() {
		return followedProjects;
	}

	public Set<Project> getLikedProjects() {
		return likedProjects;
	}

	public Set<MarketplaceItem> getMarketplaceItems() {
		return marketplaceItems;
	}

	public Set<Tag> getFieldOfActivityTags() {
		return fieldOfActivityTags;
	}

	public User setFieldOfActivityTags(Set<Tag> fieldOfActivityTags) {
		this.fieldOfActivityTags = fieldOfActivityTags;
		return this;
	}

	//Only for testing purposes
	public void setID(String id) {
		this.id = id;
	}

	public abstract User getThis();

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", loginName='" + loginName + '\'' +
				", displayName='" + displayName + '\'' +
				", hashedPassword='" + hashedPassword + '\'' +
				", description='" + description + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", city='" + location + '\'' +
				", followedProjects=" + followedProjects.stream()
				.map(Project::getName)
				.collect(Collectors.joining(",")) +
				", likedProjects=" + likedProjects.stream().map(Project::getName).collect(Collectors.joining(",")) +
				", marketplaceItems=" + marketplaceItems.stream()
				.map(MarketplaceItem::getName)
				.collect(Collectors.joining(",")) +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
