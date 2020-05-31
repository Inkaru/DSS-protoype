package bth.dss.group2.backend.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Similarity {
	@Id
	private String id;
	@DBRef(lazy = true)
	private List<User> userRanking;
	@DBRef(lazy = true)
	private List<Project> projectRanking;

	private Instant calculationDateTime;

	public Similarity(List<User> userRanking, List<Project> projectRanking, Instant calculationDateTime) {
		this.userRanking = userRanking;
		this.projectRanking = projectRanking;
		this.calculationDateTime = calculationDateTime;
	}

	public String getId() {
		return id;
	}

	public Instant getCalculationDateTime() {
		return calculationDateTime;
	}

	public Similarity setCalculationDateTime(Instant calculationDateTime) {
		this.calculationDateTime = calculationDateTime;
		return this;
	}

	public List<User> getUserRanking() {
		return userRanking;
	}

	public Similarity setUserRanking(List<User> userRanking) {
		this.userRanking = userRanking;
		return this;
	}

	public List<Project> getProjectRanking() {
		return projectRanking;
	}

	public Similarity setProjectRanking(List<Project> projectRanking) {
		this.projectRanking = projectRanking;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Similarity that = (Similarity) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

