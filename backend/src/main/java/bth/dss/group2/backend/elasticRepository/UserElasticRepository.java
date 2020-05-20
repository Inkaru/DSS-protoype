package bth.dss.group2.backend.elasticRepository;

import bth.dss.group2.backend.model.AbstractUser;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface UserElasticRepository extends ElasticsearchRepository<AbstractUser, String> {

    Page<AbstractUser> findByLoginName(String loginName, Pageable pageable);

}
