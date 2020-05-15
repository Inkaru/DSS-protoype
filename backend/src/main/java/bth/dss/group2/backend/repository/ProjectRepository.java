package bth.dss.group2.backend.repository;

import java.util.Optional;

import bth.dss.group2.backend.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
	Optional<Project> findByName(String name);

	boolean existsByName(String name);
}

