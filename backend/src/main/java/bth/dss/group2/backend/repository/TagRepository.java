package bth.dss.group2.backend.repository;

import java.util.Optional;

import bth.dss.group2.backend.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String> {
	Optional<Tag> getByName(String name);

	boolean existsByName(String name);
}
