package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Company extends User {
	private String name;

	@DBRef(lazy = true)
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
	public String toString() {
		return super.toString() + " + Company{" +
				"name='" + name + '\'' +
				", employees=" + employees.stream().map(User::getLoginName).collect(Collectors.toList()) +
				'}';
	}
}
