package bth.dss.group2.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNameExistsException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.ProjectDTO;
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
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository<User> userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	public List<ProjectDTO> getAllProjects() {
		return projectRepository.findAll().stream().map(this::getProjectDTO).collect(Collectors.toList());
	}

	public ProjectDTO getProjectById(String id) throws ProjectNotFoundException {
		return getProjectDTO(projectRepository.findById(id)
				.orElseThrow(ProjectNotFoundException::new));
	}

	public ProjectDTO getProjectByName(String name) throws ProjectNotFoundException {
		return getProjectDTO(projectRepository.findByName(name)
				.orElseThrow(ProjectNotFoundException::new));
	}

	public ProjectDTO getProjectDTO(Project project) throws ProjectNotFoundException {
		return ProjectDTO.createWithReferences(project, getProjectFollows(project), getProjectLikes(project));
	}

	private List<User> getProjectFollows(Project project) {
		return userRepository.findAllByFollowedProjectsContaining(project);
	}

	private List<User> getProjectLikes(Project project) {
		return userRepository.findAllByLikedProjectsContaining(project);
	}

	public void createProject(ProjectDTO projectDto, String creatorLoginName) throws ProjectNameExistsException, LoginNameNotFoundException {
		if (projectRepository.existsByName(projectDto.getName())) throw new ProjectNameExistsException();
		User creator = userRepository.findByLoginName(creatorLoginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.save((
				new Project())
				.name(projectDto.getName())
				.creator(creator)
				.description(projectDto.getDescription()));
		logger.info("##### PROJECT SAVED: " + project);
	}

	public void updateProject(ProjectDTO updatedProjectDto) throws ProjectNotFoundException {
		Project project = projectRepository.findById(updatedProjectDto.getId())
				.orElseThrow(ProjectNotFoundException::new);
		project.name(updatedProjectDto.getName()).description(updatedProjectDto.getDescription());
		projectRepository.save(project);
		logger.info("##### PROJECT UPDATED: " + project);
	}

	public void deleteProjectById(String id) throws ProjectNotFoundException {
		deleteProject(projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new));
	}

	public void deleteProjectByName(String name) throws ProjectNotFoundException {
		deleteProject(projectRepository.findByName(name).orElseThrow(ProjectNotFoundException::new));
	}

	private void deleteProject(Project project) {
		getProjectLikes(project).forEach(u -> {
			u.getLikedProjects().remove(project);
			userRepository.save(u);
		});
		getProjectFollows(project).forEach(u -> {
			u.getFollowedProjects().remove(project);
			userRepository.save(u);
		});
		projectRepository.delete(project);
		logger.info("##### PROJECT DELETED: " + project.getName());
	}
}
