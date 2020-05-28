package bth.dss.group2.backend.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import bth.dss.group2.backend.model.dto.MarketplaceItemDTO;
import bth.dss.group2.backend.service.MarketplaceService;
import bth.dss.group2.backend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/marketplace")
public class MarketplaceController {
	private static final Logger logger = LoggerFactory.getLogger(MarketplaceController.class);
	private final MarketplaceService marketplaceService;

	@Autowired
	public MarketplaceController(MarketplaceService marketplaceService) {
		this.marketplaceService = marketplaceService;
	}

	@GetMapping(value = "/getAllItems")
	public List<MarketplaceItemDTO> getAllItems() {
		return marketplaceService.getAll();
	}

	@GetMapping(value = "/getAllOffers")
	public List<MarketplaceItemDTO> getAllOffers() {
		return marketplaceService.getAllOffers();
	}

	@GetMapping(value = "/getAllRequests")
	public List<MarketplaceItemDTO> getAllRequests() {
		return marketplaceService.getAllRequests();
	}

	@GetMapping(value = "/getItem")
	public MarketplaceItemDTO getItem(@RequestParam String id) {
		return marketplaceService.getItem(id);
	}

	@PostMapping(value = "/createItem")
	public ResponseEntity<Void> createItem(@RequestBody @Valid final MarketplaceItemDTO marketplaceItemDTO, final Principal principal) {
		marketplaceService.create(marketplaceItemDTO, Util.getLoginName(principal));
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@PostMapping(value = "/updateItem")
	public ResponseEntity<Void> updateItem(@RequestBody @Valid final MarketplaceItemDTO marketplaceItemDTO, final Principal principal) {
		marketplaceService.update(marketplaceItemDTO, Util.getLoginName(principal));
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}

	@DeleteMapping(value = "/deleteItem")
	public ResponseEntity<Void> deleteItem(@RequestParam String id, Principal principal) {
		marketplaceService.delete(id, Util.getLoginName(principal));
		return ResponseEntity.ok().location(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
	}
}
