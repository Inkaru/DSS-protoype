package bth.dss.group2.backend;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import bth.dss.group2.backend.model.Company;
import bth.dss.group2.backend.model.MarketplaceItem;
import bth.dss.group2.backend.model.Person;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.ChatChannelDTO;
import bth.dss.group2.backend.model.dto.RegistrationDTO;
import bth.dss.group2.backend.repository.MarketplaceItemRepository;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import bth.dss.group2.backend.service.ChatService;
import bth.dss.group2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DSSApplication implements CommandLineRunner {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository<Company> companyRepository;

	@Autowired
	UserRepository<Person> personRepository;

	@Autowired
	UserRepository<User> userRepository;

	@Autowired
	UserService userService;

	@Autowired
	ChatService chatService;

	@Autowired
	MarketplaceItemRepository marketplaceItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(DSSApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		projectRepository.deleteAll();
		//Same behavior as userRepository or CompanyRepository
		userRepository.deleteAll();

		// save a couple of customers
		User timo = new Person().firstName("Timo")
				.lastName("Dittus")
				.loginName("timo")
				.email("timo@timo.timo");
		User antonin = new Person().firstName("Antonin")
				.lastName("Fleury")
				.loginName("antonin")
				.email("antonin@antonin.antonin");
		User ey = new Company().name("EY")
				.employees(Collections.singletonList((Person) timo))
				.city("Los Angeles")
				.loginName("EYTheWorst")
				.email("eytheworst@ey.ey");
		userRepository.save(timo);
		userRepository.save(antonin);
		userRepository.save(ey);
		Project test1 = new Project().name("TestProject1")
				.description("Description of text project 1")
				.creator(timo);
		projectRepository.save(test1);
		Project test2 = new Project().name("TestProject2")
				.description("Description of text project 2")
				.creator(ey);
		projectRepository.save(test2);
		userRepository.save(ey);
		userRepository.save(timo);
		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : userRepository.findAll()) {
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

		userService.createUser(new RegistrationDTO("frenchie", "test@test.test", "Encule69!", "Encule69!"));
		userService.createUser(new RegistrationDTO("mcfuckface", "fuck@fucktest.test", "ra7SVxfg9DP&Bb7miiY", "ra7SVxfg9DP&Bb7miiY"));
		userService.createUser(new RegistrationDTO("german", "german@fucktest.test", "Encule69!", "Encule69!"));

		System.out.println(userRepository.findByLoginName("frenchie"));
		MarketplaceItem item = new MarketplaceItem()
				.setPrice(844.4)
				.setName("SHIT")
				.setDescription("BUY MY SHIT")
				.setCity("Karlskrona")
				.setCountry("Sweden")
				.setType(MarketplaceItem.MarketplaceItemType.OFFER);
		marketplaceItemRepository.save(item);
		User frenchie = userRepository.findByLoginName("frenchie").get();
		frenchie.getMarketplaceItems().add(item);
		userRepository.save(frenchie);
		ChatChannelDTO channel = new ChatChannelDTO();
		channel.setParticipantLoginNames(Stream.of("frenchie", "german").collect(Collectors.toSet()));
		chatService.establishChannel(channel);
		System.out.println(item);
	}
}
