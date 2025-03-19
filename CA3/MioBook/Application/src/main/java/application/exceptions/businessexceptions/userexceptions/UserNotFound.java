package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class UserNotFound extends BusinessException {

	private boolean byUsername;

	public boolean isByUsername() {
		return byUsername;
	}

	public boolean isByEmail() {
		return !byUsername;
	}

	private UserNotFound(String msg) {
		super(msg);
	}

	public static UserNotFound emailNotFound(String email) {
		UserNotFound e = new UserNotFound("User with email " + email + " not found");
		e.byUsername = false;
		return e;
	}

	public static UserNotFound usernameNotFound(String username) {
		UserNotFound e = new UserNotFound("User with username " + username + " not found");
		e.byUsername = true;
		return e;
	}
}
