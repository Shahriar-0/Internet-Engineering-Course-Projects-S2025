package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class UsernameAlreadyExists extends BusinessException {

	private static final String MESSAGE = "Username already exists!";

	public UsernameAlreadyExists() {
		super(MESSAGE);
	}
}
