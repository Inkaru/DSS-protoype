package bth.dss.group2.backend.model;

import javax.persistence.Id;

public class User {

	@Id
	private String id;
	private String loginName;
	private String hashedPassword;
	private String firstName;
	private String lastName;
	private String title;
	private String city;
	private String country;
	private String description;
	private String emailAddress;
	private String phoneNumber;
	private String address;

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

	@Override
	public String toString() {
		//TODO: add more
		return String.format(
				"User[id=%s, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}
}
