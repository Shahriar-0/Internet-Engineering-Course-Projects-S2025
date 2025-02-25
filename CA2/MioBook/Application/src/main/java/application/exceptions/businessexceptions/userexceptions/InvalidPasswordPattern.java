package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class InvalidPasswordPattern extends BusinessException {
    private static final String MESSAGE = "Password pattern is not valid!";

    public InvalidPasswordPattern() {
        super(MESSAGE);
    }
}
