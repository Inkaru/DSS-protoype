package bth.dss.group2.backend.service;

import java.util.List;

import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(String loginName, String password, String email) throws Exception {
		if(!(userRepository.findByLoginName(loginName)==null)){
			throw new Exception("Cannot createUser, one with same loginName was already found");
		}
		String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		System.out.println("---------------------------");
		System.out.println("Here is the email"+email);
		System.out.println("---------------------------");

		if(!(email.matches(regex))){
			throw new Exception("Cannot createUser, the email is bad formatted");
		}
		if(!(userRepository.findByEmailAddress(email)==null)){
			throw new Exception("Cannot create User, the email is already linked to account");
		}
		if(password.length()<8){
			throw new Exception("Cannot create User, the password is too short");
		}
		if(password.length()>24){
			throw new Exception("Cannot create User, the password is too long");
		}
		User res = new User();
		res.loginName(loginName).hashedPassword(password).emailAddress(email);
		userRepository.save(res);
		return res;
	}
}
