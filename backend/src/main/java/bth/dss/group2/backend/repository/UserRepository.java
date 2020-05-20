package bth.dss.group2.backend.repository;

import java.util.List;
import java.util.Optional;

import bth.dss.group2.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository<T extends User> extends MongoRepository<T, String> {
    List<T> findAll();

    Optional<T> findByLoginName(String LoginName);

    Optional<T> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByLoginName(String loginName);


}