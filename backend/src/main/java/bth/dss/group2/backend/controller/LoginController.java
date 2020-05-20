package bth.dss.group2.backend.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public User getLoggedInUser(Principal principal, final HttpServletRequest httpServletRequest) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		logger.error("LOGIN NAME:" + loginName);
		try {
			return userService.getUserByLoginName(loginName);
		}
		catch (LoginNameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		}
	}
}
