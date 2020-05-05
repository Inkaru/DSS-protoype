package bth.dss.group2.backend.controller;

import java.util.List;

import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = { "/getAllUsers" }, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
