package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class WrongPassword extends BusinessException {

	private boolean byUsername;

	public boolean isByUsername() {
		return byUsername;
	}

	public boolean isByEmail() {
		return !byUsername;
	}

	private WrongPassword(String msg) {
		super(msg);
	}

	public static WrongPassword wrongPasswordForEmail(String email) {
		WrongPassword e = new WrongPassword("Wrong password for email " + email);
		e.byUsername = false;
		return e;
	}

	public static WrongPassword wrongPasswordForUsername(String username) {
		WrongPassword e = new WrongPassword("Wrong password for username " + username);
		e.byUsername = true;
		return e;
	}
}
