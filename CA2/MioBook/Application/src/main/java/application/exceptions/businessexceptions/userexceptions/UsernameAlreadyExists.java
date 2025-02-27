package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class UsernameAlreadyExists extends BusinessException {

	private static String message(String username) {
		return "User with name " + username + " already exists!";
	}

	public UsernameAlreadyExists(String username) {
		super(message(username));
	}
}
