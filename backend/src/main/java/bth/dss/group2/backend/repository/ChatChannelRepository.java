package bth.dss.group2.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import bth.dss.group2.backend.domain.ChatChannel;
import bth.dss.group2.backend.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatChannelRepository extends MongoRepository<ChatChannel, String> {
	Optional<ChatChannel> findByParticipantsContains(Set<User> participants);

	List<ChatChannel> findByParticipantsContains(User participant);
}