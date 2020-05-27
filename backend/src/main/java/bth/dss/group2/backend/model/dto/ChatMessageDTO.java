package bth.dss.group2.backend.model.dto;

import java.time.Instant;

import bth.dss.group2.backend.model.ChatMessage;

public class ChatMessageDTO {
	private String channelId;
	private String authorLoginName;
	private Instant timeSent;
	private String content;
	private boolean readByAll;

	public boolean isReadByAll() {
		return readByAll;
	}

	public ChatMessageDTO setReadByAll(boolean readByAll) {
		this.readByAll = readByAll;
		return this;
	}

	public ChatMessageDTO() {
	}

	public String getChannelId() {
		return channelId;
	}

	public ChatMessageDTO setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}

	public String getAuthorLoginName() {
		return authorLoginName;
	}

	public ChatMessageDTO setAuthorLoginName(String authorLoginName) {
		this.authorLoginName = authorLoginName;
		return this;
	}

	public Instant getTimeSent() {
		return timeSent;
	}

	public ChatMessageDTO setTimeSent(Instant timeSent) {
		this.timeSent = timeSent;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ChatMessageDTO setContent(String content) {
		this.content = content;
		return this;
	}

	public static ChatMessageDTO create(ChatMessage message, boolean readByAll) {
		ChatMessageDTO dto = new ChatMessageDTO();
		dto.setAuthorLoginName(message.getAuthor().getLoginName());
		dto.setContent(message.getContent());
		dto.setTimeSent(message.getTimeSent());
		dto.setReadByAll(readByAll);
		return dto;
	}
}
