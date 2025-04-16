package fs.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fs.user.entity.User;
import fs.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register customer
    @PostMapping("/register/customer")
    public ResponseEntity<User> registerCustomer(@RequestBody User user) {
        User registeredUser = userService.registerCustomer(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Register admin
    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody User user) {
        User registeredUser = userService.registerAdmin(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Customer login
    @PostMapping("/login/customer")
    public ResponseEntity<String> loginCustomer(@RequestBody User user) {
        String token = userService.loginCustomer(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(token);
    }

    // Admin login
    @PostMapping("/login/admin")
    public ResponseEntity<String> loginAdmin(@RequestBody User user) {
        String token = userService.loginAdmin(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(token);
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userid) {
        Optional<User> userOpt = userService.getUserById(userid);
        return userOpt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userid,
                                           @RequestBody User user) {
        User updated = userService.updateUser(userid, user);
        return ResponseEntity.ok(updated);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userid) {
        userService.deleteUser(userid);
        return ResponseEntity.ok("User deleted successfully.");
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User service is working!");
    }
}

