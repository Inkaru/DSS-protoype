package bth.dss.group2.backend.controller;

import java.util.List;

import bth.dss.group2.backend.service.HashTagService;
import bth.dss.group2.backend.util.Util;
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
@RequestMapping(path = "/api/hashtags")
public class HashTagController {
	private final HashTagService hashTagService;
	private static final Logger logger = LoggerFactory.getLogger(HashTagController.class);

	@Autowired
	public HashTagController(HashTagService hashTagService) {
		this.hashTagService = hashTagService;
	}

	@GetMapping(value = "/delete")
	public ResponseEntity<Void> deleteTag(@RequestParam String name) {
		hashTagService.delete(Util.formatHashTag(name));
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/create")
	public ResponseEntity<Void> createTag(@RequestParam String name) {
		hashTagService.create(Util.formatHashTag(name));
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@GetMapping(value = "/getAll")
	public List<String> getAllTags() {
		return hashTagService.getAll();
	}
}
