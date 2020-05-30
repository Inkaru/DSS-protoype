package bth.dss.group2.backend.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.domain.ChatChannel;
import bth.dss.group2.backend.domain.ChatMessage;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.domain.dto.ChatChannelDTO;
import bth.dss.group2.backend.domain.dto.ChatMessageDTO;
import bth.dss.group2.backend.exception.ChatChannelNotFoundException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.repository.ChatChannelRepository;
import bth.dss.group2.backend.repository.ChatMessageRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChatService {
	private final ChatChannelRepository chatChannelRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final UserRepository<User> userRepository;
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

	@Autowired
	public ChatService(ChatChannelRepository chatChannelRepository, ChatMessageRepository chatMessageRepository, UserRepository<User> userRepository) {
		this.chatChannelRepository = chatChannelRepository;
		this.chatMessageRepository = chatMessageRepository;
		this.userRepository = userRepository;
	}

	public ChatChannelDTO establishChannel(ChatChannelDTO chatChannelDTO) throws LoginNameNotFoundException {
		Set<User> participants = new HashSet<>();
		for (String name : chatChannelDTO.getParticipantLoginNames()) {
			participants.add(userRepository.findByLoginName(name).orElseThrow(LoginNameNotFoundException::new));
		}
		ChatChannel channel = chatChannelRepository.findByParticipants(participants)
				.orElseGet(() -> createChatChannel(participants));
		logger.info("CHAT CHANNEL ESTABLISHED:" + channel);
		return ChatChannelDTO.create(channel);
	}

	public List<ChatChannelDTO> getChannels(String loginName) {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		return chatChannelRepository.findByParticipantsContains(user)
				.stream()
				.map(ChatChannelDTO::create)
				.sorted(Comparator.comparing(ChatChannelDTO::getLatestMessageDate))
				.collect(Collectors.toList());
	}

	//@PreAuthorize("@chatService.canSendMessage(#channelId, #messageDto)")
	public void submitMessage(String channelId, ChatMessageDTO messageDto) {
		User author = userRepository.findByLoginName(messageDto.getAuthorLoginName())
				.orElseThrow(LoginNameNotFoundException::new);
		ChatChannel channel = chatChannelRepository.findById(channelId).orElseThrow(ChatChannelNotFoundException::new);
		ChatMessage message = new ChatMessage(author, messageDto.getContent(), Instant.now());
		chatMessageRepository.save(message);
		channel.addMessage(message);
		chatChannelRepository.save(channel);
		logger.info("CHAT MESSAGE SAVED:" + message);
	/*	userService.notifyUser(recipientUser,
				new NotificationDTO(
						"ChatMessageNotification",
						fromUser.getFullName() + " has sent you a message",
						chatMessage.getAuthorUser().getId()
				)
		);*/
	}

	public boolean canSendMessage(String channelId, ChatMessageDTO messageDto) {
		Optional<User> user = userRepository.findByLoginName(messageDto.getAuthorLoginName());
		Optional<ChatChannel> chatChannel = chatChannelRepository.findById(channelId);
		if (user.isEmpty() || chatChannel.isEmpty()) {
			//authorize in this case, so the service method throws not found exception
			return true;
		}
		return chatChannel.get().getParticipants().contains(user.get());
	}

	private ChatChannel createChatChannel(Set<User> participants) {
		ChatChannel channel = new ChatChannel(participants);
		chatChannelRepository.save(channel);
		return channel;
	}
}
