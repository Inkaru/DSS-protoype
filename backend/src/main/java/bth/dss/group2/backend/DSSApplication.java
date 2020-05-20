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
import bth.dss.group2.backend.service.UserElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("bth.dss.group2.backend.repository")
@EnableElasticsearchRepositories("bth.dss.group2.backend.elasticRepository")
@SpringBootApplication
public class DSSApplication implements CommandLineRunner {


	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	AbstractUserRepository<Company> companyRepository;

	@Autowired
	AbstractUserRepository<User> userRepository;

	@Autowired
	AbstractUserRepository<AbstractUser> abstractUserRepository;

	@Autowired
	UserElasticService userElasticService;

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
		//Clear elastic search
		elasticsearchTemplate.deleteIndex(AbstractUser.class);
		elasticsearchTemplate.createIndex(AbstractUser.class);
		elasticsearchTemplate.putMapping(AbstractUser.class);
		elasticsearchTemplate.refresh(AbstractUser.class);


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

		System.out.println("---------Test Elastic Search----------");
		System.out.println("Saving couple of user in Elastic");
		userElasticService.save(timo);
		userElasticService.save(antonin);
		userElasticService.save(ey);
		System.out.println("FindOne user by id in Elastic");
		System.out.println("--------------------------------");
		System.out.println("Should output timo : "+userElasticService.findOne(timo.getId()));
		System.out.println("FindAll user by id in Elastic");
		System.out.println("--------------------------------");
		for (AbstractUser user: userElasticService.findAll()) {
			System.out.println(user);
		}
		System.out.println("FindByLoginName user by id in Elastic");
		System.out.println("--------------------------------");
		Pageable res = PageRequest.of(0,3);
		for (AbstractUser user: userElasticService.findByLoginName(timo.getLoginName(),res).getContent()) {
			System.out.println(user);
		}
		System.out.println("Deleting all user");
		for (AbstractUser user: userElasticService.findAll()) {
			userElasticService.delete(user);
		}
		System.out.println("--------------------------------");
		System.out.println("Following printing should be directly under");
		for (AbstractUser user: userElasticService.findAll()) {
			System.out.println("Something went wrong, I had one user : "+user);
		}
		System.out.println("--------------------------------");




	}
}
