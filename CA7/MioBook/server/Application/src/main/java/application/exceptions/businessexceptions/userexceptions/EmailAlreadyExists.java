package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class EmailAlreadyExists extends BusinessException {

	private static String message(String email) {
		return "User with email '" + email + "' already exists!";
	}

	public EmailAlreadyExists(String email) {
		super(message(email));
	}
}
