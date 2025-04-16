package fs.user.servicetests;

import fs.user.entity.Role;
import fs.user.entity.User;
import fs.user.repository.UserRepository;
import fs.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = User.builder()
                .userid(1)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .address("Test Street")
                .phone(9876543210L)
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    void testRegisterCustomer() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User registered = userService.registerCustomer(testUser);
        assertNotNull(registered);
        assertEquals(Role.CUSTOMER, registered.getRole());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testRegisterAdmin() {
        testUser.setRole(Role.ADMIN);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User registered = userService.registerAdmin(testUser);
        assertNotNull(registered);
        assertEquals(Role.ADMIN, registered.getRole());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        Optional<User> result = userService.getUserById(1);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testUpdateUser() {
        User updatedUser = testUser;
        updatedUser.setName("Updated Name");

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1, updatedUser);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testLoginCustomerSuccess() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        String result = userService.loginCustomer("john@example.com", "password123");
        assertEquals("Login successful", result);
    }

    @Test
    void testLoginCustomerInvalidPassword() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        String result = userService.loginCustomer("john@example.com", "wrongpass");
        assertEquals("Invalid password", result);
    }

    @Test
    void testLoginAdminSuccess() {
        testUser.setRole(Role.ADMIN);
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        String result = userService.loginAdmin("john@example.com", "password123");
        assertEquals("Login successful", result);
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        Optional<User> result = userService.findByEmail("john@example.com");
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }
}