package bth.dss.group2.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import bth.dss.group2.backend.domain.Company;
import bth.dss.group2.backend.domain.Institution;
import bth.dss.group2.backend.domain.Person;
import bth.dss.group2.backend.domain.Project;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.domain.dto.RegistrationDTO;
import bth.dss.group2.backend.domain.dto.UserDTO;
import bth.dss.group2.backend.exception.EmailExistsException;
import bth.dss.group2.backend.exception.EmailNotFoundException;
import bth.dss.group2.backend.exception.LoginNameExistsException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.repository.MarketplaceItemRepository;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private final UserRepository<User> userRepository;
	private final LocationService locationService;
	private final TagService tagService;
	private final ProjectRepository projectRepository;
	private final MarketplaceItemRepository marketplaceItemRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository<User> userRepository, LocationService locationService, TagService tagService, ProjectRepository projectRepository, MarketplaceItemRepository marketplaceItemRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.locationService = locationService;
		this.tagService = tagService;
		this.projectRepository = projectRepository;
		this.marketplaceItemRepository = marketplaceItemRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::getUserDTO).collect(Collectors.toList());
	}

	public UserDTO getUserById(String id) throws UserNotFoundException {
		return getUserDTO(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	public UserDTO getUserByLoginName(String loginName) throws LoginNameNotFoundException {
		return getUserDTO(userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new));
	}

	public UserDTO getUserByEmail(String email) throws EmailNotFoundException {
		return getUserDTO(userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new));
	}

	private UserDTO getUserDTO(User user) {
		return UserDTO.createWithReferences(user, getCreatedProjects(user), getParticipatedProjects(user));
	}

	public void createUser(RegistrationDTO reg) throws EmailExistsException, LoginNameExistsException {
		if (userRepository.existsByEmail(reg.getEmail())) throw new EmailExistsException();
		if (userRepository.existsByLoginName(reg.getLoginName())) throw new LoginNameExistsException();
		User newUser;
		if (reg.getType() != null) {
			newUser = switch (reg.getType()) {
				case INSTITUTION -> new Institution();
				case COMPANY -> new Company();
				case PERSON -> new Person();
			};
		}
		else {
			newUser = new Person();
		}
		newUser.setLoginName(reg.getLoginName())
				.setEmail(reg.getEmail())
				.setHashedPassword(passwordEncoder.encode(reg.getPassword()));
		userRepository.save(newUser);
		logger.info("##### USER SAVED: " + newUser);
	}

	public void updateUser(UserDTO updated) throws UserNotFoundException {
		User existing = userRepository.findById(updated.getId()).orElseThrow(UserNotFoundException::new);
		// update only immutable fields (not login name, email, or id)
		existing.setDescription(updated.getDescription())
				.setDisplayName(updated.getDisplayName())
				.setPhoneNumber(updated.getPhoneNumber())
				.setFieldOfActivityTags(updated.getFieldOfActivityTags()
						.stream()
						.map(tagService::getOrCreate)
						.collect(Collectors.toSet()))
				.setLocation(locationService.getOrCreate(updated.getLocation()));
		if (updated.getType() == UserDTO.UserType.PERSON && existing instanceof Person) {
			((Person) existing).setFirstName(updated.getFirstName())
					.setLastName(updated.getLastName())
					.setTitle(updated.getTitle());
		}
		else if (updated.getType() == UserDTO.UserType.COMPANY && existing instanceof Company) {
			//((Company) existing).setName(updated.getFirstName());
		}
		else if (updated.getType() == UserDTO.UserType.INSTITUTION && existing instanceof Institution) {
			//((Institution) existing).setName(updated.getFirstName());
		}
		userRepository.save(existing);
		logger.info("##### USER UPDATED: " + existing);
	}

	public void deleteUserById(String id) throws UserNotFoundException {
		deleteUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	public void deleteUserByLoginName(String loginName) throws LoginNameNotFoundException {
		deleteUser(userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new));
	}

	public void deleteUserByEmail(String email) throws EmailNotFoundException {
		deleteUser(userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new));
	}

	public void deleteUser(User user) {
		for (Project project : projectRepository.findAllByParticipantsContaining(user)) {
			project.getParticipants().remove(user);
			projectRepository.save(project);
		}
		user.getMarketplaceItems().forEach(marketplaceItemRepository::delete);
		projectRepository.findAllByCreator(user).forEach(projectRepository::delete);
		userRepository.delete(user);
		logger.info("##### USER DELETED: " + user);
	}

	private List<Project> getCreatedProjects(User user) {
		return projectRepository.findAllByCreator(user);
	}

	private List<Project> getParticipatedProjects(User user) {
		return projectRepository.findAllByParticipantsContaining(user);
	}

	public void addLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		user.getLikedProjects().add(project);
		userRepository.save(user);
		logger.info("##### Like added, user: " + user + ", project: " + project);
	}

	public void removeLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		user.getLikedProjects().remove(project);
		userRepository.save(user);
		logger.info("##### Like removed, user: " + user + ", project: " + project);
	}

	public void addFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		user.getFollowedProjects().add(project);
		userRepository.save(user);
		logger.info("##### Follow added, user: " + user + ", project: " + project);
	}

	public void removeFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		user.getFollowedProjects().remove(project);
		userRepository.save(user);
		logger.info("##### Follow removed, user: " + user + ", project: " + project);
	}
}
