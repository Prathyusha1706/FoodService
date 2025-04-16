package fs.user.repositorytests;

import fs.user.entity.Role;
import fs.user.entity.User;
import fs.user.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test saving and finding a user by email")
    void testFindByEmailSuccess() {
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("secret")
                .address("123 Street")
                .phone(1234567890L)
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Test finding a user by non-existent email")
    void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("noexist@example.com");
        assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Test saving a user and retrieving by ID")
    void testSaveAndFindById() {
        User user = User.builder()
                .name("Another User")
                .email("another@example.com")
                .password("password")
                .address("456 Avenue")
                .phone(9876543210L)
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);
        Optional<User> fetchedUser = userRepository.findById(savedUser.getUserid());

        assertTrue(fetchedUser.isPresent());
        assertEquals("Another User", fetchedUser.get().getName());
    }
}