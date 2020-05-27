package bth.dss.group2.backend.model.dto;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import bth.dss.group2.backend.model.ChatChannel;
import bth.dss.group2.backend.model.ChatMessage;
import bth.dss.group2.backend.model.User;

public class ChatChannelDTO {

	private String id;
	@Size(min = 2)
	private Set<String> participantLoginNames;
	private List<ChatMessageDTO> messages;

	public ChatChannelDTO() {
		this.participantLoginNames = new HashSet<>();
		this.messages = new LinkedList<>();
	}

	public String getId() {
		return id;
	}

	public ChatChannelDTO setId(String id) {
		this.id = id;
		return this;
	}

	public ChatChannelDTO(Set<String> participantLoginNames) {
		this.participantLoginNames = participantLoginNames;
	}

	public Set<String> getParticipantLoginNames() {
		return participantLoginNames;
	}

	public void setParticipantLoginNames(Set<String> participantLoginNames) {
		this.participantLoginNames = participantLoginNames;
	}

	public List<ChatMessageDTO> getMessages() {
		return messages;
	}

	public ChatChannelDTO setMessages(List<ChatMessageDTO> messages) {
		this.messages = messages;
		return this;
	}

	public Instant getLatestMessageDate() {
		return messages.isEmpty() ? Instant.now() : messages.get(0).getTimeSent();
	}

	public static ChatChannelDTO create(ChatChannel channel) {
		ChatChannelDTO dto = new ChatChannelDTO();
		dto.setId(channel.getId());
		dto.setParticipantLoginNames(channel.getParticipants()
				.stream()
				.map(User::getLoginName)
				.collect(Collectors.toSet()));
		List<ChatMessageDTO> messages = new LinkedList<>();
		for (ChatMessage msg : channel.getMessages()) {
			boolean readByAll = true;
			for (User participant : channel.getParticipants()) {
				if (channel.getMessagesReadBy().containsKey(participant.getId())) {
					readByAll = readByAll && channel.getMessagesReadBy()
							.get(participant.getId())
							.compute(msg.getId(), (k, v) -> v == null ? false : v);
				}
				else {
					readByAll = false;
					break;
				}
			}
			messages.add(ChatMessageDTO.create(msg, readByAll));
		}
		messages.sort(Comparator.comparing(ChatMessageDTO::getTimeSent));
		dto.setMessages(messages);
		return dto;
	}
}
