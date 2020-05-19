package bth.dss.group2.backend.service;

import bth.dss.group2.backend.controller.AbstractUserController;
import bth.dss.group2.backend.exception.*;
import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.model.Company;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.repository.AbstractUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbstractUserService {

    private final AbstractUserRepository<AbstractUser> abstractUserRepository;
    private final AbstractUserRepository<Company> companyRepository;
    private final AbstractUserRepository<User> userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AbstractUserController.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AbstractUserService(AbstractUserRepository<AbstractUser> abstractUserRepository,AbstractUserRepository<Company> companyRepository,AbstractUserRepository<User> userRepository,PasswordEncoder passwordEncoder){
        this.abstractUserRepository = abstractUserRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<AbstractUser> getAllUsers() {
        return abstractUserRepository.findAll();
    }

    public AbstractUser getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public AbstractUser getUserByLoginName(String loginName) throws LoginNameNotFoundException {
        return userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
    }

    public AbstractUser getUserByEmail(String email) throws EmailNotFoundException {
        return userRepository.findByEmailAddress(email).orElseThrow(EmailNotFoundException::new);
    }

    public AbstractUser createUser(Registration reg) throws EmailExistsException, LoginNameExistsException {
        if (userRepository.existsByEmailAddress(reg.getEmail())) throw new EmailExistsException();
        if (userRepository.existsByLoginName(reg.getLoginName())) throw new LoginNameExistsException();
        AbstractUser newGuy = new User()
                .loginName(reg.getLoginName())
                .emailAddress(reg.getEmail())
                .hashedPassword(passwordEncoder.encode(reg.getPassword()));
        userRepository.save((User)newGuy);
        logger.info("##### USER SAVED: " + newGuy);
        return (User)newGuy;
    }

    public void updateUser(User updatedUser) throws UserNotFoundException {
        if (!userRepository.existsById(updatedUser.getId())) throw new UserNotFoundException();
        userRepository.save(updatedUser);
        logger.info("##### USER UPDATED: " + updatedUser);
    }

    public void deleteUserById(String id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + id);
    }

    public void deleteUserByLoginName(String loginName) throws LoginNameNotFoundException {
        User user = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + loginName);
    }

    public void deleteUserByEmail(String email) throws EmailNotFoundException {
        User user = userRepository.findByEmailAddress(email).orElseThrow(EmailNotFoundException::new);
        userRepository.delete(user);
        logger.info("##### USER DELETED: " + email);
    }
}
