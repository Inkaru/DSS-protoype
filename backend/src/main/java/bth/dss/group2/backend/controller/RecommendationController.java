package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.util.List;

import bth.dss.group2.backend.domain.dto.ProjectDTO;
import bth.dss.group2.backend.domain.dto.UserDTO;
import bth.dss.group2.backend.service.RecommendationService;
import bth.dss.group2.backend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/recommend/")
public class RecommendationController {

	private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);
	private final RecommendationService recommendationService;

	@Autowired
	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@GetMapping(value = "/getProjectRanking")
	public List<ProjectDTO> getAllProjects(Principal principal) {
		return recommendationService.getProjectRanking(Util.getLoginName(principal));
	}

	@GetMapping(value = "/getUserRanking")
	public List<UserDTO> getUserRanking(Principal principal) {
		return recommendationService.getMatchedUserRanking(Util.getLoginName(principal));
	}
}


