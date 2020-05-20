package bth.dss.group2.backend.service;

import bth.dss.group2.backend.model.AbstractUser;
import bth.dss.group2.backend.elasticRepository.UserElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserElasticService {

    @Autowired
    private UserElasticRepository userElasticRepository;


    public UserElasticService(UserElasticRepository userElasticRepository){
        this.userElasticRepository = userElasticRepository;
    }

    public AbstractUser save(AbstractUser user){
        return userElasticRepository.save(user);
    }

    public void delete(AbstractUser user){
        userElasticRepository.deleteById(user.getId());
    }

    public Optional<AbstractUser> findOne(String id){
        return userElasticRepository.findById(id);
    }

    public Iterable<AbstractUser> findAll(){
        return userElasticRepository.findAll();
    }

    public Page<AbstractUser> findByLoginName(String loginName, Pageable pageable){
        return userElasticRepository.findByLoginName(loginName,pageable);
    }
}
