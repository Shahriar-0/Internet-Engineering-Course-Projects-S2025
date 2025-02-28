package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class InvalidAccess extends BusinessException {

	private static String message(String access) {
		return "User does not have " + access + " access!";
	}

	public InvalidAccess(String access) {
		super(message(access));
	}
}
