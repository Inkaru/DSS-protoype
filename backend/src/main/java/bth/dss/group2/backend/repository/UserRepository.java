package bth.dss.group2.backend.repository;

import java.util.List;

import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	User findByFirstName(String firstName);
	List<User> findByLastName(String lastName);

}
