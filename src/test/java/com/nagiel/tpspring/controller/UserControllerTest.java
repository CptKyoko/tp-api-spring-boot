package com.nagiel.tpspring.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void testGetUsers() throws Exception {
//        mockMvc.perform(get("/api/users"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].username", is("Guillaume")));
//    }
//    
//    @Test
//    public void testPostUser() throws Exception {
//        // Données de test
//        String requestBody = "{\"username\": \"Bob\", \"email\": \"b@b.fr\", \"password\": \"password\"}";
//        
//        // Exécution de la requête POST
//        mockMvc.perform(post("/api/user")
//                .content(requestBody)
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("Bob"));
//    }
//    
//    @Test
//    public void testPutUser() throws Exception {
//        // Données de test
//        String requestBody = "{\"username\": \"Billy\", \"email\": \"a@a.fr\", \"password\": \"newpassword\"}";
//        
//     // Exécution de la requête PUT
//        mockMvc.perform(put("/user/{id}", 2)
//                .content(requestBody)
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("Billy"))
//                .andExpect(jsonPath("$.password").value("newpassword"));
//    
//    }
}
