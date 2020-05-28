package bth.dss.group2.backend.domain;

import java.time.Instant;
import java.util.Objects;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatMessage {
	@Indexed
	private String id;
	@DBRef(lazy = true)
	private final User author;
	private String content;

	private Instant timeSent;

	public ChatMessage(User author, String content, Instant timeSent) {
		this.author = author;
		this.content = content;
		this.timeSent = timeSent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}


	public User getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		return "ChatMessage{" +
				"id='" + id + '\'' +
				", author=" + author.getLoginName() +
				", content='" + content + '\'' +
				", timeSent=" + timeSent +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChatMessage that = (ChatMessage) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Instant getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(Instant timeSent) {
		this.timeSent = timeSent;
	}
}
