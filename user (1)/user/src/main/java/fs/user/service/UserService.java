package fs.user.service;

import java.util.List;
import java.util.Optional;

import fs.user.entity.User;

public interface UserService {
	 Optional<User> getUserById(Integer userid);
	    List<User> getAllUsers();
	    User updateUser(Integer userid, User user);
	    void deleteUser(Integer userid);
	    User registerCustomer(User user);
	    User registerAdmin(User user);
	    String loginCustomer(String email, String password);
	    String loginAdmin(String email, String password);
		Optional<User> findByEmail(String email);

}
