package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bth.dss.group2.backend.exception.EmailExistsException;
import bth.dss.group2.backend.exception.EmailNotFoundException;
import bth.dss.group2.backend.exception.LoginNameExistsException;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.RegistrationForm;
import bth.dss.group2.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/registerUser")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid final RegistrationForm registration, final HttpServletRequest httpServletRequest) {
        logger.info("##### REGISTRATION RECEIVED: " + registration);
        try {
            User user = userService.createUser(registration);
            HttpHeaders headers = new HttpHeaders();
            System.out.println(user);
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/users/registerUser")
                            .buildAndExpand(user.getId())
                            .toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        catch (EmailExistsException | LoginNameExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating profile : "+ e.getMessage(),e);
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam Optional<String> id, @RequestParam Optional<String> loginName, @RequestParam Optional<String> email, final HttpServletRequest httpServletRequest) {
        try {
            User user;
            if (id.isPresent()) {
                user = userService.getUserById(id.get());
            }
            else if (loginName.isPresent()) {
                user = userService.getUserByLoginName(loginName.get());
            }
            else if (email.isPresent()) {
                user = userService.getUserByEmail(email.get());
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }
        catch (UserNotFoundException | LoginNameNotFoundException | EmailNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody final Person user, final HttpServletRequest httpServletRequest) {
        try {
            userService.updateUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/users/updateUser")
                            .buildAndExpand(user.getId()).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user profile", e);
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam Optional<String> id, @RequestParam Optional<String> loginName, @RequestParam Optional<String> email, final HttpServletRequest httpServletRequest) {
        try {
            String str = "";
            if (id.isPresent()) {
                userService.deleteUserById(str = id.get());
            }
            else if (loginName.isPresent()) {
                userService.deleteUserByLoginName(str = loginName.get());
            }
            else if (email.isPresent()) userService.deleteUserByEmail(str = email.get());

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/users/deleteUser")
                            .buildAndExpand(str).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (UserNotFoundException | LoginNameNotFoundException | EmailNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @GetMapping(value = "/likeProject")
    public ResponseEntity<Void> likeProject(@RequestParam String id, Principal principal, final HttpServletRequest httpServletRequest) {
        try {
            String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
            userService.addLike(loginName, id);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/projects/likeProject")
                            .buildAndExpand(id).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (ProjectNotFoundException | LoginNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user", e);
        }
    }

    @GetMapping(value = "/unlikeProject")
    public ResponseEntity<Void> unlikeProject(@RequestParam String id, Principal principal, final HttpServletRequest httpServletRequest) {
        try {
            String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
            userService.removeLike(loginName, id);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/projects/unlikeProject")
                            .buildAndExpand(id).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (ProjectNotFoundException | LoginNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user", e);
        }
    }

    @GetMapping(value = "/followProject")
    public ResponseEntity<Void> followProject(@RequestParam String id, Principal principal, final HttpServletRequest httpServletRequest) {
        try {
            String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
            userService.addFollow(loginName, id);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/projects/followProject")
                            .buildAndExpand(id).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (ProjectNotFoundException | LoginNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user", e);
        }
    }

    @GetMapping(value = "/unfollowProject")
    public ResponseEntity<Void> unfollowProject(@RequestParam String id, Principal principal, final HttpServletRequest httpServletRequest) {
        try {
            String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
            userService.removeFollow(loginName, id);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromContextPath(httpServletRequest)
                            .path("/api/projects/unfollowProject")
                            .buildAndExpand(id).toUri()
            );
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        catch (ProjectNotFoundException | LoginNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user", e);
        }
    }
}
