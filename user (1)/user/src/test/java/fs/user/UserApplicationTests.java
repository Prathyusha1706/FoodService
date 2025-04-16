package fs.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
    void mainMethodRuns() {
        // Just calling the main method to ensure it runs without exceptions
        UserApplication.main(new String[]{});
    }

}
