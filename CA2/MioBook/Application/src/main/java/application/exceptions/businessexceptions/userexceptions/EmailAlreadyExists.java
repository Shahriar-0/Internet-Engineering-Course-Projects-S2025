package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class EmailAlreadyExists extends BusinessException {

	private static final String MESSAGE = "Email already exists!";

	public EmailAlreadyExists() {
		super(MESSAGE);
	}
}
