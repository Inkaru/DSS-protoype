package bth.dss.group2.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.domain.Tag;
import bth.dss.group2.backend.exception.HashTagNotFoundException;
import bth.dss.group2.backend.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TagService {
	private static final Logger logger = LoggerFactory.getLogger(TagService.class);
	private final TagRepository tagRepository;

	@Autowired
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public List<String> getAll() {
		return tagRepository.findAll().stream().map(Tag::getName).collect(Collectors.toList());
	}

	public void delete(String name) {
		Tag tag = tagRepository.getByName(name).orElseThrow(HashTagNotFoundException::new);
		tagRepository.delete(tag);
	}

	public Optional<Tag> get(String name) {
		return tagRepository.getByName(name);
	}

	public Tag getOrCreate(String name) {
		if (tagRepository.existsByName(name)) return get(name).orElseThrow(HashTagNotFoundException::new);
		return tagRepository.save(new Tag(name));
	}
}
	