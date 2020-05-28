package bth.dss.group2.backend.repository;

import java.util.List;

import bth.dss.group2.backend.domain.MarketplaceItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketplaceItemRepository extends MongoRepository<MarketplaceItem, String> {
	List<MarketplaceItem> findAllByOrderByCreated();

	List<MarketplaceItem> findAllByTypeOrderByCreated(MarketplaceItem.MarketplaceItemType type);
}
