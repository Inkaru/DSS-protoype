package bth.dss.group2.backend.repository;

import java.util.Optional;

import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findOneByEmailAddress(String emailAddress);

	Optional<User> findByLoginName(String LoginName);

	Optional<User> findByEmailAddress(String email);

	boolean existsByEmailAddress(String email);

	boolean existsByLoginName(String loginName);
}
