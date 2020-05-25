package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.RegistrationForm;
import bth.dss.group2.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

	@PostMapping(value = "/registerUser")
	public ResponseEntity<Void> registerUser(@RequestBody @Valid final RegistrationForm registration) {
		userService.createUser(registration);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping(value = "/getUser")
	public User getUser(@RequestParam Optional<String> id, @RequestParam Optional<String> loginName, @RequestParam Optional<String> email, final HttpServletRequest httpServletRequest) {
		if (id.isPresent()) {
			return userService.getUserById(id.get());
		}
		else if (loginName.isPresent()) {
			return userService.getUserByLoginName(loginName.get());
		}
		else if (email.isPresent()) {
			return userService.getUserByEmail(email.get());
		}
		else {
			throw new UserNotFoundException();
		}
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping(value = "/updateUser")
	public ResponseEntity<Void> updateUser(@RequestBody final Person user, final HttpServletRequest httpServletRequest) {
		userService.updateUser(user);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@DeleteMapping(value = "/deleteUser")
	public ResponseEntity<Void> deleteUser(@RequestParam Optional<String> id, @RequestParam Optional<String> loginName, @RequestParam Optional<String> email, final HttpServletRequest httpServletRequest) {
		if (id.isPresent()) {
			userService.deleteUserById(id.get());
		}
		else if (loginName.isPresent()) {
			userService.deleteUserByLoginName(loginName.get());
		}
		else if (email.isPresent()) {
			userService.deleteUserByEmail(email.get());
		}
		else {
			throw new UserNotFoundException();
		}
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/likeProject")
	public ResponseEntity<Void> likeProject(@RequestParam String id, Principal principal) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		userService.addLike(loginName, id);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/unlikeProject")
	public ResponseEntity<Void> unlikeProject(@RequestParam String id, Principal principal) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		userService.removeLike(loginName, id);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/followProject")
	public ResponseEntity<Void> followProject(@RequestParam String id, Principal principal) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		userService.addFollow(loginName, id);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/unfollowProject")
	public ResponseEntity<Void> unfollowProject(@RequestParam String id, Principal principal) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		userService.removeFollow(loginName, id);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}
}
