package bth.dss.group2.backend.domain;

public class Institution extends User {

	private String name;

	public Institution() {
	}

	public String getName() {
		return name;
	}

	public Institution setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public User getThis() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " + Institution{" +
				"name='" + name + '\'' +
				'}';
	}
}

