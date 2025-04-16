package fs.user.securitytests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import fs.user.security.SecurityConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)  // Import the configuration class
public class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoderBean() {
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);

        assertThat(passwordEncoder.matches(rawPassword, encoded)).isTrue();
    }

    @Test
    void testAnyRequestPermitted() throws Exception {
        mockMvc.perform(get("/any-endpoint"))
               .andExpect(status().isOk()); // Expect 200 OK since all requests are permitted
    }
}