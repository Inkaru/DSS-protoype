package bth.dss.group2.backend.service;

import java.util.List;

import bth.dss.group2.backend.controller.UserController;
import bth.dss.group2.backend.expections.EmailExistsException;
import bth.dss.group2.backend.expections.LoginNameExistsException;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

	public User createUser(Registration reg) throws Exception {
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
}
