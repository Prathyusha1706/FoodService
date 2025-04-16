package fs.user.entitytests;

import org.junit.jupiter.api.Test;

import fs.user.entity.Role;

import static org.junit.jupiter.api.Assertions.*;

class RoleTests {

    @Test
    void testEnumValues() {
        Role[] roles = Role.values();
        assertEquals(2, roles.length);
        assertArrayEquals(new Role[]{Role.CUSTOMER, Role.ADMIN}, roles);
    }

    @Test
    void testValueOfCustomer() {
        Role role = Role.valueOf("CUSTOMER");
        assertEquals(Role.CUSTOMER, role);
    }

    @Test
    void testValueOfAdmin() {
        Role role = Role.valueOf("ADMIN");
        assertEquals(Role.ADMIN, role);
    }

    @Test
    void testRolesAreNotNull() {
        for (Role role : Role.values()) {
            assertNotNull(role);
        }
    }

    @Test
    void testInvalidRoleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Role.valueOf("WORKER"); // WORKER does not exist in the enum
        });
    }
}