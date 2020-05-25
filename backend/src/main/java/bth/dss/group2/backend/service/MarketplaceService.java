package bth.dss.group2.backend.service;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.MarketplaceItemNotFoundException;
import bth.dss.group2.backend.model.MarketplaceItem;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.MarketplaceItemDTO;
import bth.dss.group2.backend.repository.MarketplaceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MarketplaceService {
	private final MarketplaceItemRepository marketplaceItemRepository;
	private final UserService userService;

	@Autowired
	public MarketplaceService(MarketplaceItemRepository marketplaceItemRepository, UserService userService) {
		this.marketplaceItemRepository = marketplaceItemRepository;
		this.userService = userService;
	}

	public void create(MarketplaceItemDTO dto, String loginName) throws LoginNameNotFoundException {
		User creator = userService.getUserByLoginName(loginName);
		MarketplaceItem item = new MarketplaceItem(dto.getName(), creator, Instant.now(), Instant.now(), dto
				.getPrice(), dto
				.getDescription(), dto.getCity(), dto.getCountry(), dto.getType());
		marketplaceItemRepository.save(item);
	}

	public List<MarketplaceItem> getAllOffers() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.OFFER);
	}

	public List<MarketplaceItem> getAllRequests() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.REQUEST);
	}

	public List<MarketplaceItem> getAll() {
		return marketplaceItemRepository.findAllByOrderByCreated();
	}

	public MarketplaceItem getItem(String id) {
		return marketplaceItemRepository.findById(id).orElseThrow(MarketplaceItemNotFoundException::new);
	}

	public void update(MarketplaceItemDTO dto, String loginName) throws LoginNameNotFoundException, MarketplaceItemNotFoundException {
		//TODO: security check to make sure user updates only his own items
		// User creator = userService.getUserByLoginName(loginName);
		MarketplaceItem item = marketplaceItemRepository.findById(dto.getId())
				.orElseThrow(MarketplaceItemNotFoundException::new);
		item
				.setName(dto.getName())
				.setDescription(dto.getDescription())
				.setCity(dto.getCity())
				.setCountry(dto.getCountry())
				.setPrice(dto.getPrice())
				.setUpdated(Instant.now());
		marketplaceItemRepository.save(item);
	}

	public void delete(String id, String loginName) throws LoginNameNotFoundException, MarketplaceItemNotFoundException {
		//TODO: security check to make sure user deletes only his own items
		// User creator = userService.getUserByLoginName(loginName);
		marketplaceItemRepository.delete(marketplaceItemRepository.findById(id)
				.orElseThrow(MarketplaceItemNotFoundException::new));
	}
}
