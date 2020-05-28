package bth.dss.group2.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.domain.HashTag;
import bth.dss.group2.backend.domain.Project;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.domain.dto.ProjectDTO;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNameExistsException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.repository.HashTagRepository;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectService {
	private final LocationService locationService;
	private final ProjectRepository projectRepository;
	private final UserRepository<User> userRepository;
	private final HashTagRepository hashTagRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	public ProjectService(LocationService locationService, ProjectRepository projectRepository, UserRepository<User> userRepository, HashTagRepository hashTagRepository) {
		this.locationService = locationService;
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.hashTagRepository = hashTagRepository;
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
		Project project = (new Project())
				.setName(projectDto.getName())
				.setCreator(userRepository.findByLoginName(creatorLoginName)
						.orElseThrow(LoginNameNotFoundException::new))
				.setDescription(projectDto.getDescription())
				.setLocation(locationService.getOrCreate(projectDto.getLocation()));
		projectDto.getHashTags().forEach(h -> addHashTag(project, h));

		projectRepository.save(project);
		logger.info("##### PROJECT SAVED: " + project);
	}

	public void updateProject(ProjectDTO updatedProjectDto) throws ProjectNotFoundException {
		Project project = projectRepository.findById(updatedProjectDto.getId())
				.orElseThrow(ProjectNotFoundException::new);
		project.setName(updatedProjectDto.getName()).setDescription(updatedProjectDto.getDescription());
		project.getHashTags().clear();
		updatedProjectDto.getHashTags().forEach(h -> addHashTag(project, h));
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

	public void addHashTag(String projectId, String tag) {
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		projectRepository.save(addHashTag(project, tag));
	}

	private Project addHashTag(Project project, String tag) {
		HashTag hashTag = hashTagRepository.findByName(tag).orElse(hashTagRepository.save(new HashTag(tag)));
		project.getHashTags().add(hashTag);
		return project;
	}

	public void removeHashTag(String projectId, String tag) {
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		projectRepository.save(removeHashTag(project, tag));
	}

	private Project removeHashTag(Project project, String tag) {
		Optional<HashTag> hashTag = hashTagRepository.findByName(tag);
		hashTag.ifPresent(value -> project.getHashTags().remove(value));
		return project;
	}
}
