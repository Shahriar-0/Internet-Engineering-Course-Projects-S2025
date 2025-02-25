package application.result;

import application.exceptions.BaseException;
import lombok.Getter;

@Getter
public class Result<T> {

	private T data;
	private BaseException exception;

	public boolean isSuccessful() {
		return exception == null;
	}

	public boolean isFailure() {
		return exception != null;
	}

	private Result(T data) {
		this.data = data;
	}

	private Result(BaseException exception) {
		this.exception = exception;
	}

	public static <T> Result<T> successOf(T data) {
		return new Result<>(data);
	}

	public static <T> Result<T> failureOf(BaseException exception) {
		return new Result<>(exception);
	}
}
