package bth.dss.group2.backend.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.service.AbstractUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path = "/api/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	private final AbstractUserService abstractUserService;

	@Autowired
	public LoginController(AbstractUserService abstractUserService) {
		this.abstractUserService = abstractUserService;
	}

	@GetMapping(value = "/failure")
	public void onLoginFailure() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	}

	@PostMapping(value = "/success")
	public AbstractUser onLoginSuccess(Principal principal, final HttpServletRequest httpServletRequest) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
		try {
			return abstractUserService.getUserByLoginName(loginName);
		}
		catch (LoginNameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
		}
	}
}
