package bth.dss.group2.backend.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person extends User {

	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String title;
	@JsonProperty
	private String address;

	public Person firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public Person lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public Person title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public Person city(String city) {
		this.city = city;
		return this;
	}

	@Override
	public Person country(String country) {
		this.country = country;
		return this;
	}

	public Person address(String address) {
		this.address = address;
		return this;
	}

	@Override
	public Person loginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	@Override
	public Person hashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return this;
	}

	@Override
	public Person description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public Person email(String email) {
		this.email = email;
		return this;
	}

	@Override
	public Person phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	@Override
	public Person likedProject(Set<Project> likedProjects) {
		this.likedProjects = likedProjects;
		return this;
	}

	@Override
	public Person followedProjects(Set<Project> followedProjects) {
		this.followedProjects = followedProjects;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return super.toString() + "+ Person{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", title='" + title + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}