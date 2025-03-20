package webapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class Response<T> extends ResponseEntity<Response.ResponseData<T>> {
    private Response(ResponseData<T> body, HttpStatus status) {
        super(body, status);
    }

    public static <T> Response<T> of(HttpStatus status) {
        ResponseData<T> body = createData(status.value(), null, null);
        return new Response<>(body, status);
    }

    public static <T> Response<T> of(T data, HttpStatus status) {
        ResponseData<T> body = createData(status.value(), null, data);
        return new Response<>(body, status);
    }

    public static <T> Response<T> of(HttpStatus status, String message) {
        ResponseData<T> body = createData(status.value(), message, null);
        return new Response<>(body, status);
    }

    public static <T> Response<T> of(T data, HttpStatus status, String message) {
        ResponseData<T> body = createData(status.value(), message, data);
        return new Response<>(body, status);
    }

    private static <T> ResponseData<T> createData(int status, String message, T data) {
        return new ResponseData<>(LocalDateTime.now(), status, message, data);
    }

    record ResponseData<T> (
            LocalDateTime timestamp,
            int status,
            String message,
            T data
    ) {}
}
