package bth.dss.group2.backend.service;

import java.util.List;

import bth.dss.group2.backend.controller.UserController;
import bth.dss.group2.backend.exception.EmailExistsException;
import bth.dss.group2.backend.exception.EmailNotFoundException;
import bth.dss.group2.backend.exception.LoginNameExistsException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.model.Company;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.RegistrationForm;
import bth.dss.group2.backend.model.dto.UserDTO;
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

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserRepository<User> userRepository;
	private final ProjectRepository projectRepository;
	private final ProjectService projectService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository<User> userRepository, ProjectRepository projectRepository, ProjectService projectService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.projectService = projectService;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserDTO getUserById(String id) throws UserNotFoundException {
		return UserDTO.createWithReferences(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
	}

	public UserDTO getUserByLoginName(String loginName) throws LoginNameNotFoundException {
		return UserDTO.createWithReferences(userRepository.findByLoginName(loginName)
				.orElseThrow(LoginNameNotFoundException::new));
	}

	public UserDTO getUserByEmail(String email) throws EmailNotFoundException {
		return UserDTO.createWithReferences(userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new));
	}

	public void createUser(RegistrationForm reg) throws EmailExistsException, LoginNameExistsException {
		if (userRepository.existsByEmail(reg.getEmail())) throw new EmailExistsException();
		if (userRepository.existsByLoginName(reg.getLoginName())) throw new LoginNameExistsException();
		User newGuy = new Person()
				.loginName(reg.getLoginName())
				.email(reg.getEmail())
				.hashedPassword(passwordEncoder.encode(reg.getPassword()));
		userRepository.save(newGuy);
		logger.info("##### USER SAVED: " + newGuy);
	}

	public void updateUser(UserDTO updated) throws UserNotFoundException {
		User existing = userRepository.findById(updated.getId()).orElseThrow(UserNotFoundException::new);
		// update only immutable fields (not login name, email, or id)
		existing.description(updated.getDescription())
				.phoneNumber(updated.getPhoneNumber())
				.city(updated.getCity())
				.country(updated.getCountry());
		if (updated.getType() == UserDTO.UserType.PERSON && existing instanceof Person) {
			((Person) existing).firstName(updated.getFirstName())
					.lastName(updated.getLastName())
					.title(updated.getTitle())
					.address(updated.getAddress());
		}
		else if (updated.getType() == UserDTO.UserType.COMPANY && existing instanceof Company) {
			//TODO: fill
		}
		else if (updated.getType() == UserDTO.UserType.INSTITUTION) {
			//TODO: fill
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
		userRepository.delete(user);
		projectService.onUserDeleted(user);
		logger.info("##### USER DELETED: " + user);
	}

	// @formatter:off
	public void addLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		modifyLikesAndFollows((u, p) -> { u.getLikedProjects().add(p);p.getLikes().add(u); }, loginName, projectId);
		logger.info("##### Like added, user: " + loginName + ", project: " + projectId);
	}

	public void removeLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		modifyLikesAndFollows((u, p) -> { u.getLikedProjects().remove(p);p.getLikes().remove(u); }, loginName, projectId);
		logger.info("##### Like removed, user: " + loginName + ", project: " + projectId);
	}

	public void addFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		modifyLikesAndFollows((u, p) -> { u.getFollowedProjects().add(p); p.getFollows().add(u); }, loginName, projectId);
		logger.info("##### Follow added, user: " + loginName + ", project: " + projectId);
	}

	public void removeFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
		modifyLikesAndFollows((u, p) -> { u.getFollowedProjects().remove(p); p.getFollows().remove(u); }, loginName, projectId);
		logger.info("##### Follow removed, user: " + loginName + ", project: " + projectId);
	}

	// @formatter:on
	private void modifyLikesAndFollows(ModifyFunction func, String loginName, String projectId) {
		User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
		func.execute(user, project);
		projectRepository.save(project);
		userRepository.save(user);
		logger.info("##### Follow removed, user: " + user + ", project: " + project);
	}

	@FunctionalInterface
	private interface ModifyFunction {
		void execute(User user, Project project);
	}
}
