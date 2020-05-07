package bth.dss.group2.backend.controller;

import java.util.List;

import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.service.UserService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = { "/user/registration/{loginName}/{password}/{email}"}, method = RequestMethod.GET)
	public User registrationForm(@PathVariable String loginName,@PathVariable String password, @PathVariable String email){
		try {
			return userService.createUser(loginName, password,email);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating profile",e);
		}

	}

	@RequestMapping(value = { "/getAllUsers" }, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

}
