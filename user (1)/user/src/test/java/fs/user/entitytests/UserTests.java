package fs.user.entitytests;

import org.junit.jupiter.api.Test;

import fs.user.entity.Role;
import fs.user.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void testUserBuilderAndGetters() {
        User user = User.builder()
                .userid(1)
                .name("John Doe")
                .email("john@example.com")
                .password("securepass")
                .address("123 Main St")
                .phone(9876543210L)
                .role(Role.CUSTOMER)
                .build();

        assertEquals(1, user.getUserid());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("securepass", user.getPassword());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(9876543210L, user.getPhone());
        assertEquals(Role.CUSTOMER, user.getRole());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();
        user.setUserid(2);
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setPassword("anotherpass");
        user.setAddress("456 Side St");
        user.setPhone(9123456780L);
        user.setRole(Role.ADMIN);

        assertEquals(2, user.getUserid());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("anotherpass", user.getPassword());
        assertEquals("456 Side St", user.getAddress());
        assertEquals(9123456780L, user.getPhone());
        assertEquals(Role.ADMIN, user.getRole());
    }
}