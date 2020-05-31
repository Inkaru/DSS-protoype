package bth.dss.group2.backend.repository;

import bth.dss.group2.backend.domain.Similarity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimilarityRepository extends MongoRepository<Similarity, String> {
}
