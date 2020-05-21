package bth.dss.group2.backend.service;

import java.util.List;

import bth.dss.group2.backend.controller.UserController;
import bth.dss.group2.backend.exception.EmailExistsException;
import bth.dss.group2.backend.exception.EmailNotFoundException;
import bth.dss.group2.backend.exception.LoginNameExistsException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.RegistrationForm;
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
    private final ProjectService projectService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository<User> userRepository, ProjectService projectService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByLoginName(String loginName) throws LoginNameNotFoundException {
        return userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
    }

    public User getUserByEmail(String email) throws EmailNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    public User createUser(RegistrationForm reg) throws EmailExistsException, LoginNameExistsException {
        if (userRepository.existsByEmail(reg.getEmail())) throw new EmailExistsException();
        if (userRepository.existsByLoginName(reg.getLoginName())) throw new LoginNameExistsException();
        User newGuy = new Person()
                .loginName(reg.getLoginName())
                .email(reg.getEmail())
                .hashedPassword(passwordEncoder.encode(reg.getPassword()));
        userRepository.save(newGuy);
        logger.info("##### USER SAVED: " + newGuy);
        return newGuy;
    }

    public void updateUser(Person updatedUser) throws UserNotFoundException {
        if (!userRepository.existsById(updatedUser.getId())) throw new UserNotFoundException();
        userRepository.save(updatedUser);
        logger.info("##### USER UPDATED: " + updatedUser);
    }

    public void deleteUserById(String id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + id);
    }

    public void deleteUserByLoginName(String loginName) throws LoginNameNotFoundException {
        User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + loginName);
    }

    public void deleteUserByEmail(String email) throws EmailNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + email);
    }

    public void addLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
        User user = getUserByLoginName(loginName);
        Project project = projectService.getProjectById(projectId);
        user.getLikedProjects().add(project);
        userRepository.save(user);
        logger.info("##### Like added, user: " + user + ", project: " + project);
    }

    public void removeLike(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
        User user = getUserByLoginName(loginName);
        Project project = projectService.getProjectById(projectId);
        user.getLikedProjects().remove(project);
        userRepository.save(user);
        logger.info("##### Like removed, user: " + user + ", project: " + project);
    }

    public void addFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
        User user = getUserByLoginName(loginName);
        Project project = projectService.getProjectById(projectId);
        user.getFollowedProjects().add(project);
        userRepository.save(user);
        logger.info("##### Follow added, user: " + user + ", project: " + project);
    }

    public void removeFollow(String loginName, String projectId) throws ProjectNotFoundException, LoginNameNotFoundException {
        User user = getUserByLoginName(loginName);
        Project project = projectService.getProjectById(projectId);
        user.getFollowedProjects().remove(project);
        userRepository.save(user);
        logger.info("##### Follow removed, user: " + user + ", project: " + project);
    }
}
