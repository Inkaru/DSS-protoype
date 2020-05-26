package bth.dss.group2.backend.repository;

import java.util.List;
import java.util.Optional;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
	Optional<Project> findByName(String name);

	List<Project> findAllByCreator(User creator);

	List<Project> findAllByParticipantsContaining(User participant);

	boolean existsByName(String name);
}

