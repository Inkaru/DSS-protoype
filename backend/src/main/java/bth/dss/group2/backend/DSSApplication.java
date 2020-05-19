package bth.dss.group2.backend;

import java.util.Collections;

import javax.transaction.Transactional;

import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.model.Company;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.Registration;
import bth.dss.group2.backend.repository.AbstractUserRepository;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.service.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DSSApplication implements CommandLineRunner {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	AbstractUserRepository<Company> companyRepository;

	@Autowired
	AbstractUserRepository<User> userRepository;

	@Autowired
	AbstractUserRepository<AbstractUser> abstractUserRepository;

	@Autowired
	AbstractUserService abstractUserService;

	public static void main(String[] args) {
		SpringApplication.run(DSSApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		projectRepository.deleteAll();
		//Same behavior as userRepository or CompanyRepository
		abstractUserRepository.deleteAll();

		// save a couple of customers
		AbstractUser timo = new User().firstName("Timo")
				.lastName("Dittus")
				.loginName("timo")
				.emailAddress("timo@timo.timo");
		AbstractUser antonin = new User().firstName("Antonin")
				.lastName("Fleury")
				.loginName("antonin")
				.emailAddress("antonin@antonin.antonin");
		AbstractUser ey = new Company().name("EY")
				.employees(Collections.singletonList((User) timo))
				.cities(Collections.singletonList("Los Angeles"))
				.loginName("EYTheWorst")
				.emailAddress("eytheworst@ey.ey");
		abstractUserRepository.save(timo);
		abstractUserRepository.save(antonin);
		abstractUserRepository.save(ey);

		projectRepository.save(new Project().name("TestProject1")
				.description("Description of text project 1")
				.creators(Collections
						.singletonList(timo)));
		projectRepository.save(new Project().name("TestProject2")
				.description("Description of text project 2")
				.creators(Collections
						.singletonList(ey)));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (AbstractUser user : userRepository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		System.out.println("Projects found with findAll():");
		System.out.println("--------------------------------");
		for (Project project : projectRepository.findAll()) {
			System.out.println(project);
		}

		System.out.println("Unmatch query response with findby():");
		System.out.println("--------------------------------");
		System.out.println(userRepository.findByLoginName("pute"));

		abstractUserService.createUser(new Registration("frenchie", "test@test.test", "Encule69!", "Encule69!"));
		System.out.println(userRepository.findByLoginName("frenchie"));
	}
}
