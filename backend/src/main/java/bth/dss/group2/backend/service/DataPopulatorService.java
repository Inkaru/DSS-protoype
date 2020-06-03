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
		generateCompanies();
		generateProjects();
		generateItems();
	}

	private void generateItems() {
		// @formatter:off
		generateItem("Designing a corporate website", "Hi, we at CompanyWithoutHomepage Ltd. are looking for someone with experience to create an interactive homepage for us. It nice and fresh and help us to display all the buzzwords we associate with out company.", "bth", MarketplaceItem.MarketplaceItemType.REQUEST);
		generateItem("Consultancy work within Europe","Hi, we at OverpaidConsultancyCompany are looking to employ YOU! We are looking for people that like to travel because we will send you from hotel to hotel every day and also on the weekends, but only in Europe. Sometimes somewhere else much further away, too though. Maybe a lot actually. But sometimes also in Europe.", "hswismar", MarketplaceItem.MarketplaceItemType.OFFER);
		generateItem("Prototyping of various applications", "We need help at prototyping various applications", "frenchie", MarketplaceItem.MarketplaceItemType.REQUEST);
		generateItem("I build ships for you", "I build ships.", "vulcan", MarketplaceItem.MarketplaceItemType.OFFER);
		// @formatter:on
	}

	private void generateItem(String name, String description, String creator, MarketplaceItem.MarketplaceItemType type) {
		MarketplaceItemDTO dto = new MarketplaceItemDTO();
		dto.setName(name)
				.setLocation(userService.getUserByLoginName(creator).getLocation())
				.setDescription(name + name + name + name + name)
				.setPrice(getRandomIntBetweenRange(1, 99999)).setType(type);
		marketplaceService.create(dto, creator);
	}

	private void generateProjects() {
		// @formatter:off
		Set<String> sustainableTags = new HashSet<>(Arrays.asList("Energy", "Green", "Sustainable"));
		Set<String> interculturalTags = new HashSet<>(Arrays.asList("Culture", "Exchange", "Baltic"));
		Set<String> recyclingTags = new HashSet<>(Arrays.asList("Recycling", "Waste", "Industrial"));
		Set<String> beerTags = new HashSet<>(Arrays.asList("British","Belgian", "Wannabe",  "Beer","Brewery" ,"Delicious"));
		Set<String> maritimeTags = new HashSet<>(Arrays.asList("Ships", "Transport", "Fishing","Gdansk", "Poland"));
		String sustainable= generateProject("GREEN FUTURE: Sustainable Energy for everyone", "Coal-power is totally 2019, we want sustainable energy and we want it now! We focus mainly on strategic solar and off-shore wind development within Europe", "klarcompany", new LocationDTO().setCity("Gdansk"), sustainableTags);
		String intercultural= generateProject("Intercultural Exchange between Baltic countries", "This project aims to exchange some culture. I give you, you give me. Good for both. But only for baltic countries, no outsiders! ", "hswismar", new LocationDTO().setCity("Wismar"), interculturalTags);
		String recycling= generateProject("Recyclomator: An Industrial waste recycling Project", "Industrial waste is bad so we try to use it again to produce goods that make industrial waste again that we can use again to produce goods that make industrial waste again that we can use again to make goods...", "frenchie", new LocationDTO().setCity("Marseille"), recyclingTags);
		String beer= generateProject("GB3C: British-Belgian Beer Brewing Cooperation","We finally want to brew great and cheap beer like the Germans, so we started this project to exchange ideas and also steal theirs!" ,"timo", new LocationDTO().setCity("München").setRegion("Bayern"), beerTags);
		String transport= generateProject("Gdansk Maritime Innovation Initiative", "The city of Gdansk wants to innovate in the field of... maritime stuff and is looking for partners!","bernie", new LocationDTO().setCity("Atlanta").setCountry("USA"), maritimeTags);
		userService.addFollow("frenchie", beer);
		userService.addFollow("timo", recycling);
		userService.addLike("timo", transport);
		userService.addLike("hswismar", sustainable);
		userService.addLike("bth", intercultural);
		// @formatter:on
	}

	private String generateProject(String name, String description, String creator, LocationDTO loc, Set<String> tags) {
		ProjectDTO dto = new ProjectDTO();
		dto.setName(name)
				.setLocation(loc)
				.setDescription(description)
				.setTags(tags);
		projectService.createProject(dto, creator);
		return projectService.getProjectByName(name).getId();
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
		generatePerson("realdonaldtrump", "Donald", "Trump", new LocationDTO().setStreetAddress("1600 Pennsylvania Ave NW").setCity("Washington").setCountry("United States"));
		generatePerson("bernie", "Bernie", "sanders", new LocationDTO().setStreetAddress("1600 Pennsylvania Ave NW").setCity("Washington").setCountry("United States"));
		generatePerson("benedictcumberbatch", "Benedict", "Cumberbatch", new LocationDTO().setStreetAddress("1600 Pennsylvania Ave NW").setCity("Washington").setCountry("United States"));
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
				.setDisplayName(firstName + " " + lastName)
				.setLocation(location)
				.setType(UserDTO.UserType.PERSON)
				.setPhoneNumber(getRandomPhoneNo());
		userService.updateUser(dto);
	}

	private void generateCompanies() {
		generateInstitution("umbrellacorp", "Umbrella Corporation", (
				new LocationDTO()).setStreetAddress("Valhallavägen 1")
				.setCity("Karlskrona")
				.setRegion("Blekinge")
				.setCountry("Sweden"));

		generateCompany("microsoft", "Microsoft Corporation", (
				new LocationDTO())
				.setCity("Redmond")
				.setCountry("USA"));
		generateCompany("klarcompany", "KLAR Company", (
				new LocationDTO())
				.setCity("Gdańsk")
				.setRegion("POMORSKIE")
				.setZipCode("80-753")
				.setCountry("Poland"));
		generateCompany("vulcan", "VULCAN Shipbuilding consultancy", (new LocationDTO())
				.setCity("Szczecin")
				.setCountry("Poland"));
	}

	private void generateCompany(String loginName, String name, LocationDTO location) {
		userService.createUser(new RegistrationDTO(loginName, loginName + "@" + loginName + ".com", "test", "test", UserDTO.UserType.INSTITUTION));
		UserDTO dto = userService.getUserByLoginName(loginName);
		dto.setDisplayName(name)
				.setLocation(location)
				.setType(UserDTO.UserType.COMPANY)
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
		dto.setDisplayName(name)
				.setLocation(location)
				.setType(UserDTO.UserType.INSTITUTION)
				.setPhoneNumber(getRandomPhoneNo());
		userService.updateUser(dto);
	}

	private String getRandomPhoneNo() {
		return ("0" + getRandomIntBetweenRange(10000000, 99999999));
	}
}
