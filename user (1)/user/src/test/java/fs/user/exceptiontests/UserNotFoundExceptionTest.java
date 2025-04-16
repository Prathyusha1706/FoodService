package fs.user.exceptiontests;

import org.junit.jupiter.api.Test;

import fs.user.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        Integer userId = 101;
        UserNotFoundException exception = new UserNotFoundException(userId);

        assertEquals("User not found with ID: 101", exception.getMessage());
    }

    @Test
    void testExceptionIsInstanceOfRuntimeException() {
        UserNotFoundException exception = new UserNotFoundException(1);

        assertTrue(exception instanceof RuntimeException);
    }
}
