package application.exceptions.userexceptions;

import application.exceptions.BaseException;

public class InvalidPasswordPattern extends BaseException {
    private static final String MESSAGE = "Password pattern is not valid!";

    public InvalidPasswordPattern() {
        super(MESSAGE);
    }
}
