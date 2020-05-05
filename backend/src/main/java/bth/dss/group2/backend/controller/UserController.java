package bth.dss.group2.backend.controller;

import bth.dss.group2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
	public Model getAllUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return model;
	}
}
