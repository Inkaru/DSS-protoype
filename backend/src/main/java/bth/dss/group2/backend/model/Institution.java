package bth.dss.group2.backend.model;

import java.util.Set;

public class Institution extends User {

	@Override
	public Institution city(String city) {
		this.city = city;
		return this;
	}

	@Override
	public Institution country(String country) {
		this.country = country;
		return this;
	}

	@Override
	public Institution loginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	@Override
	public Institution hashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
		return this;
	}

	@Override
	public Institution description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public Institution email(String email) {
		this.email = email;
		return this;
	}

	@Override
	public Institution phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	@Override
	public Institution likedProject(Set<Project> likedProjects) {
		this.likedProjects = likedProjects;
		return this;
	}

	@Override
	public Institution followedProjects(Set<Project> followedProjects) {
		this.followedProjects = followedProjects;
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " + Institution{}";
	}
}

