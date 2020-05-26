package bth.dss.group2.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import bth.dss.group2.backend.exception.LoginNameNotFoundException;
import bth.dss.group2.backend.exception.MarketplaceItemNotFoundException;
import bth.dss.group2.backend.model.MarketplaceItem;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.model.dto.MarketplaceItemDTO;
import bth.dss.group2.backend.repository.MarketplaceItemRepository;
import bth.dss.group2.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MarketplaceService {
	private final MarketplaceItemRepository marketplaceItemRepository;
	private final UserRepository<User> userRepository;

	@Autowired
	public MarketplaceService(MarketplaceItemRepository marketplaceItemRepository, UserRepository<User> userRepository) {
		this.marketplaceItemRepository = marketplaceItemRepository;
		this.userRepository = userRepository;
	}

	public void create(MarketplaceItemDTO dto, String loginName) throws LoginNameNotFoundException {
		User creator = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		MarketplaceItem item = new MarketplaceItem(dto.getName(), creator, Instant.now(), Instant.now(), dto
				.getPrice(), dto.getDescription(), dto.getCity(), dto.getCountry(), dto.getType());
		creator.getMarketplaceItems().add(marketplaceItemRepository.save(item));
		userRepository.save(creator);
	}

	public List<MarketplaceItemDTO> getAllOffers() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.OFFER)
				.stream().map(MarketplaceItemDTO::new).collect(Collectors.toList());
	}

	public List<MarketplaceItemDTO> getAllRequests() {
		return marketplaceItemRepository.findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType.REQUEST)
				.stream().map(MarketplaceItemDTO::new).collect(Collectors.toList());
	}

	public List<MarketplaceItemDTO> getAll() {
		return marketplaceItemRepository.findAllByOrderByCreated()
				.stream().map(MarketplaceItemDTO::new).collect(Collectors.toList());
	}

	public MarketplaceItemDTO getItem(String id) {
		return new MarketplaceItemDTO(marketplaceItemRepository.findById(id)
				.orElseThrow(MarketplaceItemNotFoundException::new));
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
		MarketplaceItem item = marketplaceItemRepository.findById(id)
				.orElseThrow(MarketplaceItemNotFoundException::new);
		marketplaceItemRepository.delete(item);
		User creator = userRepository.findByLoginName(loginName).orElseThrow(LoginNameNotFoundException::new);
		creator.getMarketplaceItems().remove(item);
	}
}
