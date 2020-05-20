//package bth.dss.group2.backend.controller;
//
//import bth.dss.group2.backend.controller.UserController;
//import bth.dss.group2.backend.exception.EmailExistsException;
//import bth.dss.group2.backend.exception.LoginNameExistsException;
//import bth.dss.group2.backend.model.Project;
//import bth.dss.group2.backend.model.User;
//import bth.dss.group2.backend.model.dto.Registration;
//import bth.dss.group2.backend.repository.ProjectRepository;
//import bth.dss.group2.backend.repository.UserRepository;
//import bth.dss.group2.backend.service.ProjectService;
//import bth.dss.group2.backend.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongodb.util.JSON;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.internal.matchers.Any;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.web.server.LocalServerPort;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.print.attribute.standard.Media;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@WebMvcTest(UserController.class)
//public class TestUserController {
//
//    User timo;
//    User antonin;
//
//    @MockBean
//    UserService userService;
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    ProjectService projectService;
//
//    @MockBean
//    ProjectRepository projectRepository;
//
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @BeforeEach
//    void setUp() {
//        timo = new User().firstName("Timo").lastName("Dittus").loginName("TDittus");
//        antonin = new User().firstName("Antonin").lastName("Fleury").loginName("AFleury");
//    }
//
//    @Test
//    public void testRegisterUser() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamous@gmail.com","Agoogd.Password052","Agoogd.Password052");
//        User registrationResult = new User();
//        registrationResult.loginName("NewGuyFamous").email("newguysFamous@gmail.com").hashedPassword("$2a$10$6T7zEb/OFWpz..XyqPGqheCt20WceruDH3H1ENuC1uokZlPxt0Sma").setID("unexpectedIDOnlyForTestingPurposes");
//
//        Mockito.when(userService.createUser(Mockito.any(Registration.class))).thenReturn(registrationResult);
//
//        mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testRegisterUserWithBadEmail() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamousgmail.com","Agoogd.Password052","Agoogd.Password052");
//
//        MvcResult mvcResult =  mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        String actualErrorMessage = mvcResult.getResponse().getErrorMessage();
//        System.out.println("--------------------");
//        System.out.println(actualErrorMessage);
//        System.out.println("--------------------");
//    }
//
//    @Test
//    public void testRegisterUserWithExistingEmail() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamous@gmail.com","Agoogd.Password052","Agoogd.Password052");
//        User registrationResult = new User();
//        registrationResult.loginName("NewGuyFamous").email("newguysFamous@gmail.com").hashedPassword("$2a$10$6T7zEb/OFWpz..XyqPGqheCt20WceruDH3H1ENuC1uokZlPxt0Sma").setID("unexpectedIDOnlyForTestingPurposes");
//
//        Mockito.when(userService.createUser(Mockito.any(Registration.class))).thenThrow(new EmailExistsException());
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isBadRequest()).andReturn();
//
//        String actualErrorMessage = mvcResult.getResponse().getErrorMessage();
//        String expectedErrorMessage = "Error creating profile : Email address exists already";
//
//        assertEquals(expectedErrorMessage,actualErrorMessage);
//    }
//
//    @Test
//    public void testRegisterUserWithExistingLogin() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamous@gmail.com","Agoogd.Password052","Agoogd.Password052");
//        User registrationResult = new User();
//        registrationResult.loginName("NewGuyFamous").email("newguysFamous@gmail.com").hashedPassword("$2a$10$6T7zEb/OFWpz..XyqPGqheCt20WceruDH3H1ENuC1uokZlPxt0Sma").setID("unexpectedIDOnlyForTestingPurposes");
//
//        Mockito.when(userService.createUser(Mockito.any(Registration.class))).thenThrow(new LoginNameExistsException());
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isBadRequest()).andReturn();
//        String actualErrorMessage = mvcResult.getResponse().getErrorMessage();
//        String expectedErrorMessage = "Error creating profile : Login name exists already";
//        assertEquals(expectedErrorMessage,actualErrorMessage);
//
//    }
//
//    @Test
//    public void testRegisterUserWithDifferentPassword() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamousgmail.com","Agoogd.Password052","Agoogd.Password052butDiff");
//
//        mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    public void testRegisterUserWithUnvalidPassword() throws Exception {
//        Registration registration = new Registration("NewGuyFamous","newguysFamousgmail.com","Agoogd.Password052","Agoogd.Password052");
//
//        mockMvc.perform(post("/api/users/registerUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(registration)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//
//    @Test
//    public void testGETGetAllUsers() throws Exception {
//        List<User> getAllResult;
//        getAllResult = new ArrayList<User>();
//        getAllResult.add(timo);
//        getAllResult.add(antonin);
//
//
//        Mockito.when(userService.getAllUsers()).thenReturn(getAllResult);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                .get("http://localhost:8080/api/users/getAllUsers")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].loginName").value(timo.getLoginName()))
//                .andExpect(jsonPath("$[1].loginName").value(antonin.getLoginName()));
//    }
//
//    @Test
//    public void testGetUser(){
//
//    }
//
//    @Test
//    public void testUpdateUser(){
//
//    }
//
//    @Test
//    public void testDeleteUser(){
//
//    }
//
//
//}
