package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.dto.ProjectForm;
import bth.dss.group2.backend.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

	private final ProjectService projectService;
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping(value = "/createProject")
	public ResponseEntity<Void> registerProject(@RequestBody @Valid final ProjectForm projectForm, final Principal principal) {
		String loginName = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
		projectService.createProject(projectForm, loginName);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping(value = "/getProject")
	public Project getProject(@RequestParam Optional<String> id, @RequestParam Optional<String> name) {
		if (id.isPresent()) {
			return projectService.getProjectById(id.get());
		}
		else if (name.isPresent()) {
			return projectService.getProjectByName(name.get());
		}
		else {
			throw new ProjectNotFoundException();
		}
	}

	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET)
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	@PostMapping(value = "/updateProject")
	public ResponseEntity<Void> updateProject(@RequestBody final ProjectForm projectForm) {
		projectService.updateProject(projectForm);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@DeleteMapping(value = "/deleteProject")
	public ResponseEntity<Void> deleteProject(@RequestParam Optional<String> id, @RequestParam Optional<String> name) {
		if (id.isPresent()) {
			projectService.deleteProjectById(id.get());
		}
		else if (name.isPresent()) {
			projectService.deleteProjectByName(name.get());
		}
		else {
			throw new ProjectNotFoundException();
		}
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}
}
