package bth.dss.group2.backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ChatChannel {
	@Indexed
	private String id;
	@DBRef(lazy = true)
	private List<ChatMessage> messages;
	@DBRef(lazy = true)
	private Set<User> participants;

	private Map<String, Map<String, Boolean>> messagesReadBy;

	public ChatChannel(Set<User> participants) {
		this.participants = participants;
		this.messages = new ArrayList<>();
		this.messagesReadBy = new HashMap<>();
		participants.forEach(p -> messagesReadBy.put(p.getId(), new HashMap<String, Boolean>()));
	}

	public void addMessage(ChatMessage message) {
		messages.add(message);
		messagesReadBy.values().forEach(readByMap -> readByMap.put(message.getId(), Boolean.FALSE));
	}

	public void setMessagesReadBy(ChatMessage message, User user) {
		if (messagesReadBy.containsKey(user.getId())) {
			messagesReadBy.get(user.getId()).put(message.getId(), Boolean.TRUE);
		}
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public Map<String, Map<String, Boolean>> getMessagesReadBy() {
		return messagesReadBy;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ChatChannel{" +
				"id='" + id + '\'' +
				", messages=" + messages +
				", participants=" + participants.stream().map(User::getLoginName).collect(Collectors.toList()) +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChatChannel that = (ChatChannel) o;
		return Objects.equals(id, that.id) ||
				Objects.equals(participants, that.participants);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, participants);
	}
}
