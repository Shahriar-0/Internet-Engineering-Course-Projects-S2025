package application.exceptions.userexceptions;

import application.exceptions.BaseException;

public class InvalidEmailPattern extends BaseException {
    private static final String MESSAGE = "Email pattern is not valid!";
    public InvalidEmailPattern() {
        super(MESSAGE);
    }
}
