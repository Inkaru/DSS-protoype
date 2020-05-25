package bth.dss.group2.backend.controller;

import java.security.Principal;

import bth.dss.group2.backend.model.dto.UserDTO;
import bth.dss.group2.backend.service.UserService;
import bth.dss.group2.backend.util.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	private final UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/authenticate")
	public boolean authenticate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
	}

	@RequestMapping(value = "/user")
	public UserDTO getLoggedInUser(Principal principal) {
		return userService.getUserByLoginName(ControllerUtil.getLoginName(principal));
	}
}
