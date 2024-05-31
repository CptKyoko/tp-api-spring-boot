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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.nagiel.tpspring.security.jwt.JwtUtils;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.nagiel.tpspring.service.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

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
        String password = "password";

        // Créez un objet UserDetailsImpl simulé pour l'utilisateur connecté
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, username, "b@b.fr", password, null);

        // Configurez le gestionnaire d'authentification pour renvoyer une authentification simulée
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Spécifiez l'utilisateur principal dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Configurez le JwtUtils pour renvoyer un cookie JWT simulé
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        when(jwtUtils.generateJwtCookie(any(UserDetailsImpl.class))).thenReturn(jwtCookie);

        // Effectuez une requête HTTP POST avec les informations de connexion
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk()) // Vérifier le statut de la réponse
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Vérifier le type de média de la réponse
                .andExpect(jsonPath("$.username").value(username)) // Vérifier les informations de l'utilisateur dans la réponse JSON
                .andExpect(jsonPath("$.email").value("b@b.fr"))
                .andExpect(jsonPath("$.roles").isArray()) // Vérifier que les rôles sont renvoyés en tant qu'un tableau
		        .andExpect(header().exists(HttpHeaders.SET_COOKIE)) // Vérifier que l'en-tête Set-Cookie est défini
		        .andExpect(header().string(HttpHeaders.SET_COOKIE, jwtCookie.toString())); // Vérifier le contenu du cookie JWT dans l'en-tête Set-Cookie

    }
}
