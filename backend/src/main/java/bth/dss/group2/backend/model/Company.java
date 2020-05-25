package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Company extends User {
	@JsonProperty
	private String name;

	@JsonProperty
	@DBRef
	private List<Person> employees;

	public Company() {
		super();
	}

	public Company name(String name) {
		this.name = name;
		return this;
	}

	public Company employees(List<Person> employees) {
		this.employees = employees;
		return this;
	}

	@Override
	public Company loginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	@Override
	public Company hashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return this;
	}

	@Override
	public Company description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public Company email(String email) {
		this.email = email;
		return this;
	}

	@Override
	public Company phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	@Override
	public Company city(String city) {
		this.city = city;
		return this;
	}

	@Override
	public Company country(String country) {
		this.country = country;
		return this;
	}

	@Override
	public Company likedProject(Set<Project> likedProjects) {
		this.likedProjects = likedProjects;
		return this;
	}

	@Override
	public Company followedProjects(Set<Project> followedProjects) {
		this.followedProjects = followedProjects;
		return this;
	}

	@Override
	public User createdProjects(Set<Project> createdProjects) {
		this.createdProjects = createdProjects;
		return this;
	}

	@Override
	public String toString() {
		//TODO: add more
		return String.format(
				"Company[id='%s', loginName='%s', email='%s', hashedPassword='%s', name='%s', employee='%s']",
				id, loginName, email, hashedPassword, name, employees);
	}
}
