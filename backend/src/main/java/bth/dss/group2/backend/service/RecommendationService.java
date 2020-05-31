package bth.dss.group2.backend.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bth.dss.group2.backend.domain.Project;
import bth.dss.group2.backend.domain.Similarity;
import bth.dss.group2.backend.domain.Tag;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.domain.dto.ProjectDTO;
import bth.dss.group2.backend.domain.dto.UserDTO;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.SimilarityRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
	private static final int RADIUS_EARTH = 6371;
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private final UserRepository<User> userRepository;
	private final ProjectRepository projectRepository;
	private final SimilarityRepository similarityRepository;
	private Word2Vec word2Vec;
	private boolean word2VecInitialized = true;

	@Autowired
	public RecommendationService(UserRepository<User> userRepository, ProjectRepository projectRepository, SimilarityRepository similarityRepository) {
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.similarityRepository = similarityRepository;
		try {
			File model = new ClassPathResource("word2vec/GoogleNews-vectors-negative300-SLIM.bin").getFile();
			this.word2Vec = WordVectorSerializer.readWord2VecModel(model);
			SentenceIterator iterator = new BasicLineIterator(model);
			TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
			word2Vec.setTokenizerFactory(tokenizerFactory);
			word2Vec.setSentenceIterator(iterator);
		}
		catch (IOException e) {
			logger.error("Failed to load Word2Vec model: ", e);
			word2VecInitialized = false;
		}
	}

	public List<ProjectDTO> getProjectRanking(String loginName) {
		List<ProjectDTO> ranking;
		if (!word2VecInitialized) {
			ranking = projectRepository.findAll()
					.stream()
					.map(ProjectDTO::create)
					.limit(5)
					.collect(Collectors.toList());
		}
		else {
			User user = userRepository.findByLoginName(loginName).orElseThrow(UserNotFoundException::new);
			ranking = getOrCreateUserSimilarity(user).getProjectRanking()
					.stream()
					.map(ProjectDTO::create)
					.limit(5)
					.collect(Collectors.toList());
		}
		return ranking;
	}

	public List<UserDTO> getMatchedUserRanking(String loginName) {
		List<UserDTO> ranking;
		if (!word2VecInitialized) {
			ranking = userRepository.findAll().stream().map(UserDTO::create).limit(5).collect(Collectors.toList());
		}
		else {
			User user = userRepository.findByLoginName(loginName).orElseThrow(UserNotFoundException::new);
			ranking = getOrCreateUserSimilarity(user).getUserRanking()
					.stream()
					.map(UserDTO::create)
					.limit(5)
					.collect(Collectors.toList());
		}
		return ranking;
	}

	private Similarity getOrCreateUserSimilarity(User user) {
		Similarity similarity = user.getSimilarity();
		if (similarity == null) {
			similarity = calculateUserSimilarity(user);
			similarityRepository.save(similarity);
		}
		//calculate a new similarity if the previous one is older than a day
		else if (similarity.getCalculationDateTime().isBefore(Instant.now().minus(1, ChronoUnit.DAYS))) {
			similarityRepository.delete(similarity);
			similarity = calculateUserSimilarity(user);
			similarityRepository.save(similarity);
		}
		user.setSimilarity(similarity);
		userRepository.save(user);
		return similarity;
	}

	private Similarity calculateUserSimilarity(User user) {
		HashMap<User, HashMap<String, Double>> similarityMap = new HashMap<>();
		Set<Tag> userProjectTags = getProjectTags(user);
		for (User otherUser : userRepository.findAll()) {
			if (otherUser.equals(user)) continue;
			HashMap<String, Double> sims = new HashMap<>();
			Set<Tag> otherUserProjectTags = getProjectTags(otherUser);
			sims.put("projectSimilarity", getAverageTagSimilarity(userProjectTags, otherUserProjectTags));
			sims.put("fieldOfActivitySimilarity", getAverageTagSimilarity(user.getFieldOfActivityTags(), otherUser.getFieldOfActivityTags()));
			sims.put("locationSimilarity", 0.2 * getLocationSimilarity(user, otherUser));
			similarityMap.put(otherUser, sims);
		}
		HashMap<User, Double> averageSimilarityMap = new HashMap<>();
		similarityMap.forEach((k, v) -> averageSimilarityMap.put(k, calculateAverageSimilarity(v)));
		List<User> userRanking = averageSimilarityMap.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
		Set<Project> alreadyKnownProjects = new HashSet<>();
		alreadyKnownProjects.addAll(user.getFollowedProjects());
		alreadyKnownProjects.addAll(user.getLikedProjects());
		alreadyKnownProjects.addAll(projectRepository.findAllByParticipantsContaining(user));
		alreadyKnownProjects.addAll(projectRepository.findAllByCreator(user));
		List<Project> projectRanking = getProjectRanking(userRanking, alreadyKnownProjects);
		return new Similarity(userRanking, projectRanking, Instant.now());
	}

	private Set<Tag> getProjectTags(User user) {
		Set<Tag> userProjectTags = new HashSet<>();
		userProjectTags.addAll(getLikedTags(user));
		userProjectTags.addAll(getFollowedTags(user));
		userProjectTags.addAll(getParticipateTags(user));
		userProjectTags.addAll(getCreatedTags(user));
		return userProjectTags;
	}

	private List<Project> getProjectRanking(List<User> userRanking, Set<Project> alreadyKnown) {
		HashMap<Project, Integer> countProjects = new HashMap<>();
		int countUsers = 0;
		int k = 10;
		// get most liked/participated/followed projects of the k most similar users
		for (User user : userRanking) {
			countUsers++;
			List<Project> likedFollowedParticipated = new ArrayList<>();
			likedFollowedParticipated.addAll(projectRepository.findAllByParticipantsContaining(user));
			likedFollowedParticipated.addAll(user.getFollowedProjects());
			likedFollowedParticipated.addAll(user.getLikedProjects());
			for (Project project : likedFollowedParticipated) {
				// skip already known projects
				if (alreadyKnown.contains(project)) continue;
				countProjects.compute(project, (p, s) -> (s == null) ? 1 : s++);
			}
			if (countUsers >= k) {
				//cancel when k is reached, but expand k if there's not at least 4 projects found
				if (countProjects.keySet().size() < 5) {
					k++;
				}
				else {
					break;
				}
			}
		}
		return countProjects.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private double calculateAverageSimilarity(HashMap<String, Double> similarities) {
		List<Double> finalSims = new ArrayList<>();
		finalSims.add(similarities.get("projectSimilarity"));
		finalSims.add(similarities.get("fieldOfActivitySimilarity"));
		finalSims.add(similarities.get("locationSimilarity"));
		double sumOfSims = 0;
		int j = 0;
		for (Double s : finalSims) {
			if (s == null) continue;
			j++;
			sumOfSims += s;
		}
		return j == 0 ? 0 : (sumOfSims / j);
	}

	private Set<Tag> getFollowedTags(User user) {
		return user.getFollowedProjects()
				.stream()
				.map(Project::getTags)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	private Set<Tag> getLikedTags(User user) {
		return user.getLikedProjects()
				.stream()
				.map(Project::getTags)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	private Set<Tag> getParticipateTags(User user) {
		return projectRepository.findAllByParticipantsContaining(user)
				.stream().map(Project::getTags)
				.flatMap(Collection::stream).collect(Collectors.toSet());
	}

	private Set<Tag> getCreatedTags(User user) {
		return projectRepository.findAllByCreator(user)
				.stream().map(Project::getTags)
				.flatMap(Collection::stream).collect(Collectors.toSet());
	}

	private Double getAverageTagSimilarity(Set<Tag> set1, Set<Tag> set2) {
		if (set1.isEmpty() || set2.isEmpty()) return null;
		int fieldsOfActvitySimilarity = 0;
		int count = 0;
		for (Tag tag1 : set1) {
			for (Tag tag2 : set2) {
				fieldsOfActvitySimilarity += calculateWordSimilarity(tag1.getName(), tag2.getName());
				count++;
			}
		}
		return count != 0 ? fieldsOfActvitySimilarity / (double) count : 0;
	}

	private double calculateWordSimilarity(String word1, String word2) {
		return word2Vec.similarity(word1, word2);
	}

	public Double getLocationSimilarity(User user, User otherUser) {
		if (user.getLocation() == null || otherUser.getLocation() == null) return null;
		Double userLat = user.getLocation().getLatitude();
		Double userLon = user.getLocation().getLatitude();
		Double otherUserLat = otherUser.getLocation().getLatitude();
		Double otherUserLon = otherUser.getLocation().getLatitude();
		if (userLat == null || userLon == null || otherUserLat == null || otherUserLon == null) {
			return getLocationWordSimilarity(user, otherUser);
		}
		double d = getHaversineDistance(userLat, userLon, otherUserLat, otherUserLon);
		//normalize the distance to a similarity: distance =< 50km is max similarity (1) and distance >= 1500 km is min (0)
		d = Math.max(30, d);
		d = Math.min(1500, d);
		double normalized = 1 - (d - 30) / (1500 - 30);
		return normalized;
	}

	private Double getLocationWordSimilarity(User user, User otherUser) {
		HashMap<String, String> locationWords = new HashMap<>();
		locationWords.put(user.getLocation().getCity(), otherUser.getLocation().getCity());
		locationWords.put(user.getLocation().getRegion(), otherUser.getLocation().getRegion());
		locationWords.put(user.getLocation().getCountry(), otherUser.getLocation().getCountry());
		int i = 0;
		double cumulativeSimilarity = 0;
		for (String word1 : locationWords.keySet()) {
			if (word1 == null) continue;
			for (String word2 : locationWords.values()) {
				if (word2 == null) continue;
				i++;
				cumulativeSimilarity += calculateWordSimilarity(word1, word2);
			}
		}
		return i == 0 ? null : cumulativeSimilarity / (double) i;
	}

	private double getHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLong = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double a = haversin(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return RADIUS_EARTH * c;
	}

	private double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}
}
