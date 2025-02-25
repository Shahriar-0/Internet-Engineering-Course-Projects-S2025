package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class InvalidUsernamePattern extends BusinessException {

	private static final String MESSAGE = "Username pattern is not valid!";

	public InvalidUsernamePattern() {
		super(MESSAGE);
	}
}
