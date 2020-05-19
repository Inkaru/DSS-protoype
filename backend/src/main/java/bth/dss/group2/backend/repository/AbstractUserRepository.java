package bth.dss.group2.backend.repository;

import bth.dss.group2.backend.model.AbstractUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AbstractUserRepository<T extends AbstractUser> extends MongoRepository<T, String> {
    List<T> findAll();

    Optional<T> findByLoginName(String LoginName);

    Optional<T> findByEmailAddress(String email);

    boolean existsByEmailAddress(String email);

    boolean existsByLoginName(String loginName);


}