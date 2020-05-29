package bth.dss.group2.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tag")
public class Tag {
	@Id
	private String id;

	private String name;

	public Tag(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public Tag setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Tag setName(String name) {
		this.name = name;
		return this;
	}
}
