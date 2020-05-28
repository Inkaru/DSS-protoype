package bth.dss.group2.backend.repository;

import bth.dss.group2.backend.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
