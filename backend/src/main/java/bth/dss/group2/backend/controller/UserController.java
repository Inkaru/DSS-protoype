package bth.dss.group2.backend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/registration")
	public ResponseEntity<Void> registerUser(@RequestBody @Valid final Registration registration, final HttpServletRequest httpServletRequest) {
		logger.info("##### REGISTRATION RECEIVED: " + registration);
		try {
			User user = userService.createUser(registration);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ServletUriComponentsBuilder
							.fromContextPath(httpServletRequest)
							.path("/api/users/registration")
							.buildAndExpand(user.getId()).toUri()
			);
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating profile", e);
		}
	}

	@RequestMapping(value = { "/getAllUsers" }, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
