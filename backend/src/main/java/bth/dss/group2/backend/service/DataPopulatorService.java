package bth.dss.group2.backend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import bth.dss.group2.backend.domain.MarketplaceItem;
import bth.dss.group2.backend.domain.dto.LocationDTO;
import bth.dss.group2.backend.domain.dto.MarketplaceItemDTO;
import bth.dss.group2.backend.domain.dto.ProjectDTO;
import bth.dss.group2.backend.domain.dto.RegistrationDTO;
import bth.dss.group2.backend.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataPopulatorService {
	private final UserService userService;
	private final ProjectService projectService;
	private final MarketplaceService marketplaceService;
	private final ChatService chatService;

	@Autowired
	public DataPopulatorService(UserService userService, ProjectService projectService, MarketplaceService marketplaceService, ChatService chatService) {
		this.userService = userService;
		this.projectService = projectService;
		this.marketplaceService = marketplaceService;
		this.chatService = chatService;
	}

	public static int getRandomIntBetweenRange(int min, int max) {
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	public void populate() {
		generatePersons();
		generateInstitutions();
		generateProjects();
		generateItems();
	}

	private void generateItems() {
		// @formatter:off
		generateItem("Swedish meatballs", "bth", MarketplaceItem.MarketplaceItemType.REQUEST);
		generateItem("Escort service", "hswismar", MarketplaceItem.MarketplaceItemType.OFFER);
		generateItem("Half of one baguette", "frenchie", MarketplaceItem.MarketplaceItemType.REQUEST);
		generateItem("Delicious German beer", "timo", MarketplaceItem.MarketplaceItemType.OFFER);
		// @formatter:on
	}

	private void generateItem(String name, String creator, MarketplaceItem.MarketplaceItemType type) {
		MarketplaceItemDTO dto = new MarketplaceItemDTO();
		dto.setName(name)
				.setLocation(userService.getUserByLoginName(creator).getLocation())
				.setDescription(name + name + name + name + name)
				.setPrice(getRandomIntBetweenRange(1, 99999)).setType(type);
		marketplaceService.create(dto, creator);
	}

	private void generateProjects() {
		// @formatter:off
		generateProject("Sustainable Energy Project", "bth", new LocationDTO().setCity("Stockholm"));
		generateProject("Intercultural Exchange Project", "hswismar", new LocationDTO().setCity("Wismar"));
		generateProject("French Baguette Project", "frenchie", new LocationDTO().setCity("Marseille"));
		generateProject("Awesome German Beer Brewing Project", "timo", new LocationDTO().setCity("München").setRegion("Bayern"));
		// @formatter:on
	}

	private void generateProject(String name, String creator, LocationDTO loc) {
		ProjectDTO dto = new ProjectDTO();
		dto.setName(name)
				.setLocation(loc)
				.setDescription(name + name + name + name + name)
				.setHashTags(getRandomHashTags());
		projectService.createProject(dto, creator);
	}

	private Set<String> getRandomHashTags() {
		List<String> tags = Arrays.asList(
				"#Enginnering",
				"#University",
				"#Education",
				"#Creative",
				"#Traditional",
				"#Artistic",
				"#Architecture",
				"#Design",
				"#Photography",
				"#Advertising",
				"#Maritime",
				"#Tourism",
				"#Transport",
				"#Renewables",
				"#Energy"
		);
		Set<String> result = new HashSet<>();
		Random rand = new Random();
		int n = rand.nextInt(4);
		for (int i = 0; i < n; i++) {
			result.add(tags.get(rand.nextInt(tags.size())));
		}
		return result;
	}

	private void generatePersons() {
		// @formatter:off
		generatePerson("timo", "Timo", "Dittus", new LocationDTO().setStreetAddress("Minervavägen 22A").setCity("371 41 Karlskrona").setCountry("Sweden"));
		generatePerson("antonin", "Antonin", "Fleury", new LocationDTO().setStreetAddress("82 Boulevard de Clichy").setCity("Paris").setCountry("France"));
		generatePerson("frenchie", "François", "Français", new LocationDTO().setStreetAddress("5 Avenue Anatole").setCity("Paris").setCountry("France"));
		generatePerson("mcfuckface", "Donald", "Trump", new LocationDTO().setStreetAddress("1600 Pennsylvania Ave NW").setCity("Washington").setCountry("United States"));
		// @formatter:on
	}

	private void generatePerson(String loginName, String firstName, String lastName, LocationDTO location) {
		//frenchie gets special pw
		String pw = loginName.equals("frenchie") ? "Encule69!" : "test";
		userService.createUser(new RegistrationDTO(loginName, loginName + "@" + loginName + ".com", pw, pw, UserDTO.UserType.PERSON));
		UserDTO dto = userService.getUserByLoginName(loginName);
		dto.
				setFirstName(firstName)
				.setLastName(lastName)
				.setLocation(location)
				.setType(UserDTO.UserType.INSTITUTION)
				.setPhoneNumber(getRandomPhoneNo());
		userService.updateUser(dto);
	}

	private void generateInstitutions() {
		generateInstitution("bth", "Blekinge Tekniska Högskola", (
				new LocationDTO()).setStreetAddress("Valhallavägen 1")
				.setCity("Karlskrona")
				.setRegion("Blekinge")
				.setCountry("Sweden"));

		generateInstitution("hswismar", "Hochschule Wismar", (
				new LocationDTO()).setStreetAddress("Philipp-Müller-Straße 14")
				.setCity("Wismar")
				.setCountry("Germany"));
	}

	private void generateInstitution(String loginName, String name, LocationDTO location) {
		userService.createUser(new RegistrationDTO(loginName, loginName + "@" + loginName + ".com", "test", "test", UserDTO.UserType.INSTITUTION));
		UserDTO dto = userService.getUserByLoginName(loginName);
		dto.setName(name)
				.setLocation(location)
				.setType(UserDTO.UserType.INSTITUTION)
				.setPhoneNumber(getRandomPhoneNo());
		userService.updateUser(dto);
	}

	private String getRandomPhoneNo() {
		return ("0" + getRandomIntBetweenRange(10000000, 99999999));
	}
}
