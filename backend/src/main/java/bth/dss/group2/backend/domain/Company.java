package bth.dss.group2.backend.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Company extends User {
	@DBRef(lazy = true)
	private Set<Person> employees;

	public Company() {
		this.employees = new HashSet<>();
	}

	public Set<Person> getEmployees() {
		return employees;
	}

	public Company setEmployees(Set<Person> employees) {
		this.employees = employees;
		return this;
	}

	@Override
	public User getThis() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " + Company{" +
				", employees=" + employees.stream().map(User::getLoginName).collect(Collectors.toList()) +
				'}';
	}
}
