package fs.item.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fs.item.entity.User;

@FeignClient(name = "USER-SERVICE",  url = "http://localhost:9002")
public interface UserClient {
    @GetMapping("/users/{userid}")
    User getUserById(@PathVariable("userid") Integer userid);
}
