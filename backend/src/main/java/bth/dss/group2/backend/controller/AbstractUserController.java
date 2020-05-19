package bth.dss.group2.backend.controller;

import bth.dss.group2.backend.exception.*;
import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.service.AbstractUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
public class AbstractUserController {

    private static final Logger logger = LoggerFactory.getLogger(AbstractUserController.class);
    private final AbstractUserService userService;

    @Autowired
    public AbstractUserController(AbstractUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registerUser")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid final Registration registration, final HttpServletRequest httpServletRequest) {
        logger.info("##### REGISTRATION RECEIVED: " + registration);
        try {
            AbstractUser user = userService.createUser(registration);
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
    public AbstractUser getUser(@RequestParam Optional<String> id, @RequestParam Optional<String> loginName, @RequestParam Optional<String> email, final HttpServletRequest httpServletRequest) {
        try {
            AbstractUser user;
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
    public List<AbstractUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody final User user, final HttpServletRequest httpServletRequest) {
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
}
