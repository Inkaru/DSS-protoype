package bth.dss.group2.backend;

import java.util.Arrays;
import java.util.Collections;

import javax.transaction.Transactional;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.ProjectRepository;
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
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;

	public static void main(String[] args) {
		SpringApplication.run(DSSApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		projectRepository.deleteAll();

		// save a couple of customers
		User timo = new User().firstName("Timo").lastName("Dittus");
		User antonin = new User().firstName("Antonin").lastName("Fleury");
		userRepository.save(timo);
		userRepository.save(antonin);

		projectRepository.save(new Project().name("TestProject1").description("Description of text project 1").creators(Collections
				.singletonList(timo)));
		projectRepository.save(new Project().name("TestProject2").description("Description of text project 2").creators(Collections
				.singletonList(antonin)));

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

		System.out.println("Projects found with findAll():");
		System.out.println("--------------------------------");
		for (Project project : projectRepository.findAll()) {
			System.out.println(project);
		}

		System.out.println("Unmatch query response with findby():");
		System.out.println("--------------------------------");
		System.out.println(userRepository.findByLoginName("pute"));

	}
}
