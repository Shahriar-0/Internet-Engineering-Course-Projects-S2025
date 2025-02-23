package application.exceptions.userexceptions;

import application.exceptions.BaseException;

public class EmailAlreadyExists extends BaseException {
    private static final String MESSAGE = "Email already exists!";
    public EmailAlreadyExists() {
        super(MESSAGE);
    }
}
