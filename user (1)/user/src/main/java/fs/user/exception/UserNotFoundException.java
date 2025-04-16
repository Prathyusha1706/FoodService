package fs.user.exception;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(Integer userid) {
		super("User not added with ID: " + userid);
	}

}
