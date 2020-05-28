package bth.dss.group2.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class HashTag {
	@Indexed
	@Id
	private String id;
	@Indexed(unique = true)
	private String name;

	public HashTag(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public HashTag setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public HashTag setName(String name) {
		this.name = name;
		return this;
	}
}
