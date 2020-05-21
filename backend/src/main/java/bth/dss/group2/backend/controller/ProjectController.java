package bth.dss.group2.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import bth.dss.group2.backend.exception.ProjectNameExistsException;
import bth.dss.group2.backend.exception.ProjectNotFoundException;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.dto.ProjectForm;
import bth.dss.group2.backend.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
	public ResponseEntity<Void> registerProject(@RequestBody @Valid final ProjectForm projectForm, final HttpServletRequest httpServletRequest) {

		try {
			Project project = projectService.createProject(projectForm);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ServletUriComponentsBuilder
							.fromContextPath(httpServletRequest)
							.path("/api/projects/createProject")
							.buildAndExpand(project.getId())
							.toUri()
			);
			logger.info("##### Created project: " + project);
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
		catch (ProjectNameExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating project : " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping(value = "/getProject")
	public Project getProject(@RequestParam Optional<String> id, @RequestParam Optional<String> name, final HttpServletRequest httpServletRequest) {
		try {
			Project Project;
			if (id.isPresent()) {
				Project = projectService.getProjectById(id.get());
			}
			else if (name.isPresent()) {
				Project = projectService.getProjectByName(name.get());
			}
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
			}
			return Project;
		}
		catch (ProjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found", e);
		}
	}

	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET)
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	@PostMapping(value = "/updateProject")
	public ResponseEntity<Void> updateProject(@RequestBody final ProjectForm projectForm, final HttpServletRequest httpServletRequest) {
		try {
			Project project = projectService.updateProject(projectForm);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ServletUriComponentsBuilder
							.fromContextPath(httpServletRequest)
							.path("/api/projects/updateProject")
							.buildAndExpand(project.getId()).toUri()
			);
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
		catch (ProjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating project", e);
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@DeleteMapping(value = "/deleteProject")
	public ResponseEntity<Void> deleteProject(@RequestParam Optional<String> id, @RequestParam Optional<String> name, final HttpServletRequest httpServletRequest) {
		try {
			String str = "";
			if (id.isPresent()) {
				projectService.deleteProjectById(str = id.get());
			}
			else if (name.isPresent()) {
				projectService.deleteProjectByName(str = name.get());
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
					ServletUriComponentsBuilder
							.fromContextPath(httpServletRequest)
							.path("/api/projects/deleteProject")
							.buildAndExpand(str).toUri()
			);
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}
		catch (ProjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found", e);
		}
	}
}
