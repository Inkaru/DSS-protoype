package bth.dss.group2.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.domain.Location;
import bth.dss.group2.backend.domain.MarketplaceItem;
import bth.dss.group2.backend.domain.User;
import bth.dss.group2.backend.domain.dto.MarketplaceItemDTO;
import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.MarketplaceItemNotFoundException;
import bth.dss.group2.backend.exception.UserNotFoundException;
import bth.dss.group2.backend.repository.MarketplaceItemRepository;
import bth.dss.group2.backend.repository.UserRepository;
import bth.dss.group2.backend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MarketplaceService {
	private final MarketplaceItemRepository marketplaceItemRepository;
	private final UserRepository<User> userRepository;
	private static final Logger logger = LoggerFactory.getLogger(MarketplaceService.class);

	@Autowired
	public MarketplaceService(MarketplaceItemRepository marketplaceItemRepository, UserRepository<User> userRepository) {
		this.marketplaceItemRepository = marketplaceItemRepository;
		this.userRepository = userRepository;
	}

	public void create(MarketplaceItemDTO dto, String loginName) throws LoginNameNotFoundException {
		User creator = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		MarketplaceItem item = new MarketplaceItem(dto.getName(), Instant.now(), Instant.now(), dto
				.getPrice(), dto.getDescription(), Util.getMapper()
				.map(dto.getLocation(), Location.class), dto.getType());
		creator.getMarketplaceItems().add(item);
		marketplaceItemRepository.save(item);
		userRepository.save(creator);
		logger.info("##### MARKETPLACEITEM SAVED:" + item);
	}

	public List<MarketplaceItemDTO> getAllOffers() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.OFFER)
				.stream().map(this::getItemDTO).collect(Collectors.toList());
	}

	public List<MarketplaceItemDTO> getAllRequests() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.REQUEST)
				.stream().map(this::getItemDTO).collect(Collectors.toList());
	}

	public List<MarketplaceItemDTO> getAll() {
		return marketplaceItemRepository.findAllByOrderByCreated()
				.stream().map(this::getItemDTO).collect(Collectors.toList());
	}

	public MarketplaceItemDTO getItem(String id) {
		return getItemDTO(marketplaceItemRepository.findById(id).orElseThrow(MarketplaceItemNotFoundException::new));
	}

	public MarketplaceItemDTO getItemDTO(MarketplaceItem item) {
		User user = userRepository.findByMarketplaceItemsContains(item).orElseThrow(UserNotFoundException::new);
		return MarketplaceItemDTO.createWithReferences(item, user);
	}

	public void update(MarketplaceItemDTO dto, String loginName) throws LoginNameNotFoundException, MarketplaceItemNotFoundException {
		//TODO: security check to make sure user updates only his own items
		// User creator = userService.getUserByLoginName(loginName);
		MarketplaceItem item = marketplaceItemRepository.findById(dto.getId())
				.orElseThrow(MarketplaceItemNotFoundException::new);
		Util.getMapper().map(dto, item);
		item.setUpdated(Instant.now());
		marketplaceItemRepository.save(item);
	}

	public void delete(String id, String loginName) throws LoginNameNotFoundException, MarketplaceItemNotFoundException {
		//TODO: security check to make sure user deletes only his own items
		// User creator = userService.getUserByLoginName(loginName);
		MarketplaceItem item = marketplaceItemRepository.findById(id)
				.orElseThrow(MarketplaceItemNotFoundException::new);
		marketplaceItemRepository.delete(item);
		User creator = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		creator.getMarketplaceItems().remove(item);
		userRepository.save(creator);
	}
}
