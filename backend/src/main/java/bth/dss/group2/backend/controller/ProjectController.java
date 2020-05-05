package bth.dss.group2.backend.controller;

import java.util.List;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.service.ProjectService;
import bth.dss.group2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(value = { "/getAllProjects" }, method = RequestMethod.GET)
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}
}
