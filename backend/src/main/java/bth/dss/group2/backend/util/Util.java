package bth.dss.group2.backend.util;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;

public class Util {
	public static String getLoginName(Principal principal) {
		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
	}

	public static String formatHashTag(String hashTag) {
		if (hashTag == null || hashTag.isBlank()) return hashTag;
		if (hashTag.charAt(0) != '#') hashTag = "#" + hashTag;
		return hashTag;
	}

	public static Set<String> formatHashTags(Set<String> hashTags) {
		if (hashTags == null) return null;
		HashSet<String> formattedTags = new HashSet<>();
		hashTags.forEach(str -> formattedTags.add(formatHashTag(str)));
		return formattedTags;
	}

	public static ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
}
