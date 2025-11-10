package com.app.lends_backend.controller;

import com.app.lends_backend.model.User;
import com.app.lends_backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Limpiamos la base de datos antes de cada test para evitar conflictos
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterFirstAdminUserSuccessfully() throws Exception {
        // 1. Arrange
        User userToRegister = new User(
                null,
                "Jane",
                "Doe",
                "Engineer",
                "54321",
                "jane.doe@example.com",
                "securePass123",
                "USER"
        );

        // 2. Act & Assert (Petición HTTP)
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToRegister)))
                .andExpect(status().isCreated()) // Verificamos el estado 201 Created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber()) // Verificamos que la respuesta tiene un ID
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist()); // ¡Muy importante! El DTO no debe exponer la contraseña.

        // 3. Assert (Verificación en Base de Datos)
        assertThat(userRepository.count()).isEqualTo(1);
        User savedUser = userRepository.findAll().getFirst();

        assertThat(savedUser.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(savedUser.getPassword()).isNotEqualTo("securePass123"); // La contraseña debe estar codificada
        assertThat(passwordEncoder.matches("securePass123", savedUser.getPassword())).isTrue(); // Verificamos que el hash corresponde al password original
    }
}