package fs.user.controllertests;

import fs.user.controller.UserController;
import fs.user.entity.Role;
import fs.user.entity.User;
import fs.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        testUser = User.builder()
                .userid(1)
                .name("John")
                .email("john@example.com")
                .password("pass")
                .address("Street")
                .phone(1234567890L)
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    void testRegisterCustomer() throws Exception {
        Mockito.when(userService.registerCustomer(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/users/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testRegisterAdmin() throws Exception {
        testUser.setRole(Role.ADMIN);
        Mockito.when(userService.registerAdmin(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/users/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void testLoginCustomer() throws Exception {
        Mockito.when(userService.loginCustomer(eq("john@example.com"), eq("pass")))
                .thenReturn("Customer token");

        mockMvc.perform(post("/users/login/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer token"));
    }

    @Test
    void testGetUserById() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(testUser));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        Mockito.when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/email?email=john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(eq(1), any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));
    }

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/users/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("User service is working!"));
    }
}
