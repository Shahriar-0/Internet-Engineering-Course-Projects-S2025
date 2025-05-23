package webapi.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static <T> Response<T> of(T data, HttpStatus status, String message, String jwtToken) {
        ResponseData<T> body = createData(status.value(), message, data);
        HttpHeaders header = createHeaderWithJwt(jwtToken);
        return new Response<>(body, status, header);
    }

    private static HttpHeaders createHeaderWithJwt(String jwtToken) {
        HttpHeaders header = new HttpHeaders();
        if (jwtToken != null) {
            header.set("Authorization", "Bearer " + jwtToken);
            header.set("Access-Control-Expose-Headers", "Authorization");
        }
        return header;
    }

    private static <T> ResponseData<T> createData(int status, String message, T data) {
        return new ResponseData<>(LocalDateTime.now(), status, message, data);
    }
}
