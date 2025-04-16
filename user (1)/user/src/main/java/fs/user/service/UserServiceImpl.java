package fs.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fs.user.entity.Role;
import fs.user.entity.User;
import fs.user.exception.UserNotFoundException;
import fs.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserById(Integer userid) {
        return userRepository.findById(userid);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Integer userid, User user) {
        return userRepository.findById(userid).map(existing -> {
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
            existing.setAddress(user.getAddress());
            existing.setPhone(user.getPhone());
            existing.setRole(user.getRole());
            return userRepository.save(existing);
        }).orElseThrow(() -> new UserNotFoundException(userid));
    }

    @Override
    public void deleteUser(Integer userid) {
        if (!userRepository.existsById(userid)) {
            throw new UserNotFoundException(userid);
        }
        userRepository.deleteById(userid);
    }

    @Override
    public User registerCustomer(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Customer already exists with email: " + user.getEmail());
        }
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registered = userRepository.save(user);
        log.info("Customer registered: {}", user.getEmail());
        return registered;
    }

    @Override
    public User registerAdmin(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Admin already exists with email: " + user.getEmail());
        }
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registered = userRepository.save(user);
        log.info("Admin registered: {}", user.getEmail());
        return registered;
    }

    @Override
    public String loginCustomer(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getRole().equals(Role.CUSTOMER)) {
            throw new RuntimeException("Not a customer account.");
        }

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.info("Customer login successful: {}", email);
            return "Customer login successful!";
        } else {
            log.warn("Customer login failed: {}", email);
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public String loginAdmin(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Not an admin account.");
        }

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.info("Admin login successful: {}", email);
            return "Admin login successful!";
        } else {
            log.warn("Admin login failed: {}", email);
            throw new RuntimeException("Invalid email or password");
        }
    }

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
}
