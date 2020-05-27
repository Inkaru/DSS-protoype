package bth.dss.group2.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import bth.dss.group2.backend.model.ChatChannel;
import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatChannelRepository extends MongoRepository<ChatChannel, String> {
	Optional<ChatChannel> findByParticipants(Set<User> participants);

	List<ChatChannel> findByParticipantsContains(User participant);
}