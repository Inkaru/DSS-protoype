package bth.dss.group2.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.domain.HashTag;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.exception.HashTagNotFoundException;
import bth.dss.group2.backend.repository.HashTagRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HashTagService {
	private final HashTagRepository hashTagRepository;
	private static final Logger logger = LoggerFactory.getLogger(HashTagService.class);

	@Autowired
	public HashTagService(HashTagRepository hashTagRepository, UserRepository<User> userRepository) {
		this.hashTagRepository = hashTagRepository;
	}

	public List<String> getAll() {
		return hashTagRepository.findAll().stream().map(HashTag::getName).collect(Collectors.toList());
	}

	public void delete(String name) {
		HashTag tag = hashTagRepository.findByName(name).orElseThrow(HashTagNotFoundException::new);
		hashTagRepository.delete(tag);
	}

	public void create(String name) {
		hashTagRepository.save(new HashTag(name));
	}

}
	