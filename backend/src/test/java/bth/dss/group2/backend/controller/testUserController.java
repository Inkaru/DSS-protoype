package bth.dss.group2.backend.controller;

import bth.dss.group2.backend.controller.UserController;
import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import bth.dss.group2.backend.service.ProjectService;
import bth.dss.group2.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class testUserController {

    User timo;
    User antonin;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProjectService projectService;

    @MockBean
    ProjectRepository projectRepository;


/*
    ObjectMapper mapper = new ObjectMapper();
*/

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        timo = new User().firstName("Timo").lastName("Dittus").loginName("TDittus");
        antonin = new User().firstName("Antonin").lastName("Fleury").loginName("AFleury");
    }


    @Test
    public void testGETGetAllUsers() throws Exception {
        List<User> getAllResult;
        getAllResult = new ArrayList<User>();
        getAllResult.add(timo);
        getAllResult.add(antonin);


        Mockito.when(userService.getAllUsers()).thenReturn(getAllResult);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/users/getAllUsers")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].loginName").value(timo.getLoginName()))
                .andExpect(jsonPath("$[1].loginName").value(antonin.getLoginName()));

        /*this.mockMvc.perform(get("api/users/getAllUsers"));
        *//*.content(mapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))*//*
        System.out.println("hey there");*/
    }



}
