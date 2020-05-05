package bth.dss.group2.backend.service;

import java.util.List;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
}
