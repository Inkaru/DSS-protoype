package bth.dss.group2.backend.repository;

import bth.dss.group2.backend.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
	Project findByName(String name);
}
