package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class User {
	@Id
	private String id;
	@JsonProperty
	private String loginName;
	private String hashedPassword;
	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String title;
	@JsonProperty
	private String city;
	@JsonProperty
	private String country;
	@JsonProperty
	private String description;
	@JsonProperty
	private String emailAddress;
	@JsonProperty
	private String phoneNumber;
	@JsonProperty
	private String address;
	@JsonProperty

	@DBRef
	private List<Project> followedProjects;

	public User loginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public User hashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return this;
	}

	public User firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public User lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public User title(String title) {
		this.title = title;
		return this;
	}

	public User city(String city) {
		this.city = city;
		return this;
	}

	public User country(String country) {
		this.country = country;
		return this;
	}

	public User description(String description) {
		this.description = description;
		return this;
	}

	public User emailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public User phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public User address(String address) {
		this.address = address;
		return this;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		//TODO: add more
		return String.format(
				"User[id=%s, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return loginName.equals(user.loginName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getLoginName() {
		return loginName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<Project> getFollowedProjects() {
		return followedProjects;
	}
}
