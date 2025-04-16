package fs.menu.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import fs.menu.entity.User;
@FeignClient(name = "user-service", url = "http://localhost:9002")
public interface UserClient {

    @GetMapping("/users/{userid}")
    User getUserById(@PathVariable int userid);
}