package bth.dss.group2.backend.domain;

public class Person extends User {

	private String firstName;
	private String lastName;
	private String title;

	public String getFirstName() {
		return firstName;
	}

	public Person setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Person setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Person setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public User getThis() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + "+ Person{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", title='" + title + '\'' +
				'}';
	}
}