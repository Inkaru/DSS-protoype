package bth.dss.group2.backend;

import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class DSSApplication implements CommandLineRunner {
	@Autowired UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(DSSApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();

		// save a couple of customers
		userRepository.save(new User().firstName("Timo").lastName("Dittus"));
		userRepository.save(new User().firstName("Antonin").lastName("Fleury"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : userRepository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Timo'):");
		System.out.println("--------------------------------");
		System.out.println(userRepository.findByFirstName("Timo"));

		System.out.println("Customers found with findByLastName('Fleury'):");
		System.out.println("--------------------------------");
		for (User user : userRepository.findByLastName("Fleury")) {
			System.out.println(user);
		}

	}
}
