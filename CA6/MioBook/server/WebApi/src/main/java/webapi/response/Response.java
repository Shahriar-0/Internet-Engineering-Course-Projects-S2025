package webapi.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import webapi.services.AuthenticationService;

import java.time.LocalDateTime;

public class Response<T> extends ResponseEntity<ResponseData<T>> {
    private Response(ResponseData<T> body, HttpStatus status) {
        super(body, status);
    }

    private Response(ResponseData<T> body, HttpStatus status, HttpHeaders header) {
        super(body, header, status);
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

    public static <T> Response<T> of(T data, HttpStatus status, String message, String sessionId) {
        ResponseData<T> body = createData(status.value(), message, data);
        HttpHeaders header = createHeader(sessionId);
        return new Response<>(body, status, header);
    }

    private static HttpHeaders createHeader(String sessionId) {
        HttpHeaders header = new HttpHeaders();
        header.set(AuthenticationService.SESSION_KEY_STR, sessionId);
        return header;
    }

    private static <T> ResponseData<T> createData(int status, String message, T data) {
        return new ResponseData<>(LocalDateTime.now(), status, message, data);
    }
}
