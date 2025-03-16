package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class UsernameAlreadyExists extends BusinessException {

	private static String message(String username) {
		return "User with username '" + username + "' already exists!";
	}

	public UsernameAlreadyExists(String username) {
		super(message(username));
	}
}
