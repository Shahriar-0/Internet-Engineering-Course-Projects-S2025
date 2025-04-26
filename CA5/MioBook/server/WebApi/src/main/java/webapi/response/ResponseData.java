package webapi.response;

import java.time.LocalDateTime;

record ResponseData<T> (
        LocalDateTime timestamp,
        int status,
        String message,
        T data
) {}
