package webapi.services;

import application.exceptions.businessexceptions.BusinessException;
import application.exceptions.dataaccessexceptions.DataAccessException;
import application.exceptions.dataaccessexceptions.EntityAlreadyExists;
import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import webapi.exceptions.AuthenticationException;
import webapi.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String METHOD_ARGUMENT_VALIDATION_MESSAGE = "Method arguments are not valid";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return Response.of(errors, BAD_REQUEST, METHOD_ARGUMENT_VALIDATION_MESSAGE);
	}

	@ExceptionHandler(DataAccessException.class)
	public Response<?> handleDataAccessExceptions(DataAccessException ex) {
		if (ex instanceof EntityAlreadyExists)
			return Response.of(CONFLICT, ex.getMessage());
		if (ex instanceof EntityDoesNotExist)
			return Response.of(NOT_FOUND, ex.getMessage());

		return Response.of(BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public Response<?> handleBusinessExceptions(BusinessException ex) {
		return Response.of(BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public Response<?> handleAuthenticationExceptions(AuthenticationException ex) {
		if (ex.isNoOneLoggedInException())
			return Response.of(UNAUTHORIZED, ex.getMessage());

		return Response.of(BAD_REQUEST, ex.getMessage());
	}
}
