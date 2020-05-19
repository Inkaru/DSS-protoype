package bth.dss.group2.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import bth.dss.group2.backend.model.*;
import bth.dss.group2.backend.repository.AbstractUserRepository;
import bth.dss.group2.backend.repository.ProjectRepository;
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
		AbstractUser timo = new User().firstName("Timo").lastName("Dittus").loginName("timo").emailAddress("timo@timo.timo");
		AbstractUser antonin = new User().firstName("Antonin").lastName("Fleury").loginName("antonin").emailAddress("antonin@antonin.antonin");
		AbstractUser ey = new Company().name("EY").employees(Collections.singletonList((User)timo)).cities(Collections.singletonList("Los Angeles")).loginName("EYTheWorst").emailAddress("eytheworst@ey.ey");
		abstractUserRepository.save(timo);
		abstractUserRepository.save(antonin);
		abstractUserRepository.save(ey);

		List<AbstractUser> testProject1Creators = new ArrayList<AbstractUser>();
		testProject1Creators.add((User)timo);

		List<AbstractUser> testProject2Creators = new ArrayList<AbstractUser>();
		testProject2Creators.add((User)antonin);
		testProject2Creators.add((Company)ey);

		Project project1 = new Project().name("TestProject1").description("Description of text project 1").creators(testProject1Creators);
		Project project2 = new Project().name("TestProject2").description("Description of text project 2").creators(testProject2Creators);
		System.out.println("------- Project before database --------");
		System.out.println(project1);
		System.out.println(project2);
		System.out.println("-----------------");


		projectRepository.save(project1);
		projectRepository.save(project2);

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
	}


}
