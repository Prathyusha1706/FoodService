package fs.user.exception;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(Integer userid) {
		super("User not found with ID: " + userid);
	}

}
