package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class InvalidEmailPattern extends BusinessException {

	private static final String MESSAGE = "Email pattern is not valid!";

	public InvalidEmailPattern() {
		super(MESSAGE);
	}
}
