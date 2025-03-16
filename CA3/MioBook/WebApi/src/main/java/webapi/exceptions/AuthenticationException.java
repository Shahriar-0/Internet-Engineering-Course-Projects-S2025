package webapi.exceptions;

public class AuthenticationException extends RuntimeException {
    private boolean anyOneLoggedIn;

    public boolean isNoOneLoggedInException() {
        return !anyOneLoggedIn;
    }

    public boolean isAnyOneLoggedInException() {
        return anyOneLoggedIn;
    }

    private AuthenticationException(String msg) {
        super(msg);
    }

    public static AuthenticationException noOneLoggedIn() {
        AuthenticationException e = new AuthenticationException("No one logged in");
        e.anyOneLoggedIn = false;
        return e;
    }

    public static AuthenticationException someOneLoggedIn() {
        AuthenticationException e = new AuthenticationException("Some one logged in");
        e.anyOneLoggedIn = true;
        return e;
    }
}
