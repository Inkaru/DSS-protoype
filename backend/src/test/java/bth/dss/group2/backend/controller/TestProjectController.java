package bth.dss.group2.backend.controller;

import bth.dss.group2.backend.model.Project;
import bth.dss.group2.backend.model.User;
import bth.dss.group2.backend.repository.ProjectRepository;
import bth.dss.group2.backend.repository.UserRepository;
import bth.dss.group2.backend.service.ProjectService;
import bth.dss.group2.backend.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//FIXME UNABLE TO GET MOCKITO WORKING TO MOCK USER
@WebMvcTest(ProjectController.class)
public class TestProjectController {

/*    User timo = Mockito.mock(User.class);
    User antonin = Mockito.mock(User.class);*/

    User timo, antonin;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProjectService projectService;

    @MockBean
    ProjectRepository projectRepository;

    Project timosProject, antoninsProject;

    List<User> timosProjectCreators, antoninsProjectCreators;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        /* Enter Mockito.when function User here. If they are modify during test should be move in BeforeEach hook*/
       /* Mockito.when(timo.getFirstName()).thenReturn("Timo");
        Mockito.when(timo.getLastName()).thenReturn("Dittus");
        Mockito.when(timo.getLoginName()).thenReturn("TDittus");

        Mockito.when(antonin.getFirstName()).thenReturn("Antonin");
        Mockito.when(antonin.getLastName()).thenReturn("Fleury");
        Mockito.when(antonin.getLoginName()).thenReturn("AFleury");*/

       timo = new User();
       antonin = new User();
       /*timo.loginName("TDittus").firstName("Timo").lastName("Dittus");
       antonin.loginName("AFleury").firstName("Antonin").lastName("Fleury");*/

        timosProjectCreators = new ArrayList<User>();
        timosProjectCreators.add(timo);
        antoninsProjectCreators = new ArrayList<User>();
        antoninsProjectCreators.add(antonin);
        antoninsProjectCreators.add(timo);


        timosProject = new Project();
        timosProject.creators(timosProjectCreators).name("DSS Suport system").description("Lorem Ipsum");

        antoninsProject = new Project();
        antoninsProject.creators(antoninsProjectCreators).name("DSS Suport system with cooperation of timo").description("Lorem Ipsum dolores ombrage");
    }

    @Test
    void testGETGetAllProject() throws Exception {
        List<Project> getAllResult;
        getAllResult = new ArrayList<Project>();
        getAllResult.add(timosProject);
        getAllResult.add(antoninsProject);

        Mockito.when(projectService.getAllProjects()).thenReturn(getAllResult);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/projects/getAllProjects")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
                /*.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].loginName").value(timo.getLoginName()))
                .andExpect(jsonPath("$[1].loginName").value(antonin.getLoginName()));
*/
    }



}
