package bth.dss.group2.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import bth.dss.group2.backend.controller.UserController;
import bth.dss.group2.backend.exception.EmailExistsException;
import bth.dss.group2.backend.exception.EmailNotFoundException;
import bth.dss.group2.backend.exception.LoginNameExistsException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
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
		return userRepository.findByEmailAddress(email).orElseThrow(EmailNotFoundException::new);
	}

	public User createUser(Registration reg) throws EmailExistsException, LoginNameExistsException {
		if (userRepository.existsByEmailAddress(reg.getEmailAddress())) throw new EmailExistsException();
		if (userRepository.existsByLoginName(reg.getLoginName())) throw new LoginNameExistsException();
		User newGuy = new User()
				.loginName(reg.getLoginName())
				.emailAddress(reg.getEmailAddress())
				.hashedPassword(passwordEncoder.encode(reg.getPassword()));
		userRepository.save(newGuy);
		logger.info("##### USER SAVED: " + newGuy);
		return newGuy;
	}

	public void updateUser(User updatedUser) throws UserNotFoundException {
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
		User user = userRepository.findByEmailAddress(email).orElseThrow(EmailNotFoundException::new);
		userRepository.delete(user);
		logger.info("##### USER DELETED: " + email);
	}
}
