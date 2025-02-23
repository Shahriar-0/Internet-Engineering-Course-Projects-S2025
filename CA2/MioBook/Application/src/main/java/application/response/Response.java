package application.response;

import application.exceptions.BaseException;

public class Response<T> {
    private T data;
    private BaseException exception;
    public boolean isSuccessful() {
        return exception == null;
    }
}
