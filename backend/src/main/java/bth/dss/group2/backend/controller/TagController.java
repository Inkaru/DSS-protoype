package bth.dss.group2.backend.controller;

import java.util.List;

import bth.dss.group2.backend.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/tags")
public class TagController {
	private static final Logger logger = LoggerFactory.getLogger(TagController.class);
	private final TagService tagService;

	@Autowired
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping(value = "/delete")
	public ResponseEntity<Void> deleteTag(@RequestParam String name) {
		//name = Util.formatHashTag(name);
		tagService.delete(name);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/create")
	public ResponseEntity<Void> createTag(@RequestParam String name) {
		//name = Util.formatHashTag(name);
		tagService.getOrCreate(name);
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/getAll")
	public List<String> getAllTags() {
		return tagService.getAll();
	}
}
