package bth.dss.group2.backend.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tag")
public class Tag {
	@Id
	private String id;

	@Indexed(unique = true)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tag tag = (Tag) o;
		return Objects.equals(id, tag.id) &&
				Objects.equals(name, tag.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
