package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

import javax.validation.Valid;

import bth.dss.group2.backend.model.dto.ChatChannelDTO;
import bth.dss.group2.backend.model.dto.ChatMessageDTO;
import bth.dss.group2.backend.service.ChatService;
import bth.dss.group2.backend.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/chat")
public class ChatController {

	private final ChatService chatService;

	@Autowired
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@MessageMapping("/chat/{channelId}")
	@SendTo("/topic/chat.{channelId}")
	public ChatMessageDTO sendMessage(@DestinationVariable String channelId, @Payload ChatMessageDTO message, Principal principal) {
		message.setAuthorLoginName(Util.getLoginName(principal));
		message.setTimeSent(Instant.now());
		chatService.submitMessage(channelId, message);
		return message;
	}

	@MessageMapping("/chat/publicChat")
	@SendTo("/topic/publicChat")
	public ChatMessageDTO sendPublicMessage(@Payload ChatMessageDTO message, Principal principal) {
		message.setAuthorLoginName(Util.getLoginName(principal));
		message.setTimeSent(Instant.now());
		return message;
	}

	@PostMapping(value = "/establishChannel")
	public ChatChannelDTO establishChatChannel(@RequestBody @Valid ChatChannelDTO channel, Principal principal) {
		//add own login name in case it has been forgotten
		channel.getParticipantLoginNames().add(Util.getLoginName(principal));
		return chatService.establishChannel(channel);
	}

	@GetMapping(value = "/getMyChannels")
	public List<ChatChannelDTO> getMyChannels(Principal principal) {
		return chatService.getChannels(Util.getLoginName(principal));
	}
}