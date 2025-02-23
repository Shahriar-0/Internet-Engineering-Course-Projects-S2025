package application.exceptions.userexceptions;

import application.exceptions.BaseException;

public class InvalidUsernamePattern extends BaseException {
    private static final String MESSAGE = "Username pattern is not valid!";
    public InvalidUsernamePattern() {
        super(MESSAGE);
    }
}
