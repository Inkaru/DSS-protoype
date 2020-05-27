package bth.dss.group2.backend.repository;

import bth.dss.group2.backend.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
