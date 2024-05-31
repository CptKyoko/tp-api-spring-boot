package com.nagiel.tpspring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagiel.tpspring.security.jwt.JwtUtils;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.nagiel.tpspring.service.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser() throws Exception {
        // Données de test
        String requestBody = "{\"username\": \"Bob\", \"email\": \"b@b.fr\", \"password\": \"password\"}";

        // Exécution de la requête POST
        mockMvc.perform(post("/api/auth/signup")
                .content(requestBody)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
    
    @Test
    public void testAuthenticateUser() throws Exception {
        // Données de test
        String username = "Bob";
        String requestBody = "{\"username\": \"Bob\", \"password\": \"password\"}";

        // Créer un UserDetails factice
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,               // id
                "Bob",            // username
                "b@b.fr",         // email
                "password",       // password
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")) // authorities
        );

        // Configurer le mock AuthenticationManager pour renvoyer une Authentication valide
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities()));

        // Effectuez une requête HTTP POST avec les informations de connexion
        mockMvc.perform(post("/api/auth/signin")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Vérifier le statut de la réponse
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Vérifier le type de média de la réponse
                .andExpect(jsonPath("$.username").value(username)) // Vérifier les informations de l'utilisateur dans la réponse JSON
                .andExpect(jsonPath("$.email").value("b@b.fr"))
                .andExpect(jsonPath("$.roles").isArray()) // Vérifier que les rôles sont renvoyés en tant qu'un tableau
                .andExpect(header().exists(HttpHeaders.SET_COOKIE)); // Vérifier que l'en-tête Set-Cookie est défini
    }


}
