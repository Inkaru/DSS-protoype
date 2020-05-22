package bth.dss.group2.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import bth.dss.group2.backend.controller.ProjectController;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNameExistsException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.ProjectForm;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final UserRepository<User> userRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository<User> userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Project getProjectById(String id) throws ProjectNotFoundException {
		return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
	}

	public Project getProjectByName(String name) throws ProjectNotFoundException {
		return projectRepository.findByName(name).orElseThrow(ProjectNotFoundException::new);
	}

	public Project createProject(ProjectForm projectForm, String creatorLoginName) throws ProjectNameExistsException, LoginNameNotFoundException {
		if (projectRepository.existsByName(projectForm.getName())) throw new ProjectNameExistsException();
		User creator = userRepository.findByLoginName(creatorLoginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.save((
				new Project())
				.name(projectForm.getName())
				.addCreator(creator)
				.description(projectForm.getDescription()));
		logger.info("##### PROJECT SAVED: " + project);
		return project;
	}

	public Project updateProject(ProjectForm updatedProjectForm) throws ProjectNotFoundException {
		Project project = projectRepository.findById(updatedProjectForm.getId())
				.orElseThrow(ProjectNotFoundException::new);
		project.name(updatedProjectForm.getName()).description(updatedProjectForm.getDescription());
		projectRepository.save(project);
		logger.info("##### PROJECT UPDATED: " + project);
		return project;
	}

	public void deleteProjectById(String id) throws ProjectNotFoundException {
		Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
		projectRepository.delete(project);
		logger.info("##### PROJECT DELETED: " + id);
	}

	public void deleteProjectByName(String name) throws ProjectNotFoundException {
		Project project = projectRepository.findByName(name).orElseThrow(ProjectNotFoundException::new);
		projectRepository.delete(project);
		logger.info("##### PROJECT DELETED: " + name);
	}
}
