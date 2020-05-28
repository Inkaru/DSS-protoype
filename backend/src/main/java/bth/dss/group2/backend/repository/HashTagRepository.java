package bth.dss.group2.backend.repository;

import java.util.Optional;

import bth.dss.group2.backend.model.HashTag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HashTagRepository extends MongoRepository<HashTag, String> {
	Optional<HashTag> findByName(String name);
}
