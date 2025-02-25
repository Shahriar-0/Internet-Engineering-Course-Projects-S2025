package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class InvalidAccess extends BusinessException {

	private static final String MESSAGE = "Invalid access!";

	public InvalidAccess() {
		super(MESSAGE);
	}
}
