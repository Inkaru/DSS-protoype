package bth.dss.group2.backend.domain;

public class Institution extends User {


	public Institution() {
	}

	@Override
	public User getThis() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " + Institution{" +
				'}';
	}
}

