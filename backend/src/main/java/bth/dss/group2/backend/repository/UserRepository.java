package bth.dss.group2.backend.repository;

import java.util.List;
import java.util.Optional;

import bth.dss.group2.backend.model.MarketplaceItem;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository<T extends User> extends MongoRepository<T, String> {

	Optional<T> findByLoginName(String LoginName);

	Optional<T> findByEmail(String email);

	Optional<T> findByMarketplaceItemsContains(MarketplaceItem item);

	List<T> findAllByFollowedProjectsContaining(Project project);

	List<T> findAllByLikedProjectsContaining(Project project);

	boolean existsByEmail(String email);

	boolean existsByLoginName(String loginName);
}