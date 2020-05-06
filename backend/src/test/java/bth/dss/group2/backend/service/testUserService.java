package bth.dss.group2.backend.service;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.UserRepository;
import bth.dss.group2.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class testUserService {

    public UserRepository userRepository = Mockito.mock(UserRepository.class);
    public UserService userService;

    public User timo;
    public User antonin;
    public List<User> userRepositoryContent;



    @BeforeEach
    void setUp() {

/*         Test are independant of classe, so following line would be bad but much more simplier*/

        timo = new User().firstName("Timo").lastName("Dittus").loginName("TDittus");
        antonin = new User().firstName("Antonin").lastName("Fleury").loginName("AFleury");

        /* Otherwise we should create mock like that
        timo = Mockito.mock(User.class);
        Mockito.when()
        antonin = Mockito.mock(User.class);
        */


        userRepositoryContent = new ArrayList<User>();
        userRepositoryContent.add(timo);
        userRepositoryContent.add(antonin);

        Mockito.when(userRepository.findAll()).thenReturn(userRepositoryContent);

        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers(){
        List<User> res = userService.getAllUsers();
        System.out.println(res);
        assertThat(res).contains(timo).contains(antonin);
    }

}
