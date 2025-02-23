package application.response;

import application.exceptions.BaseException;
public class Response<T> {
    private T data;
    private BaseException exception;
    public boolean isSuccessful() {
        return exception == null;
    }

    private Response (T data) {
        this.data = data;
    }
    private Response (BaseException exception) {
        this.exception = exception;
    }
    public static <T> Response<T> successOf(T data) {
        return new Response<>(data);
    }
    public static <T> Response<T> failureOf(BaseException exception) {
        return new Response<>(exception);
    }
    public static <T> Response<T> failureOf(String msg) {
        return new Response<>(new BaseException(msg));
    }
}
