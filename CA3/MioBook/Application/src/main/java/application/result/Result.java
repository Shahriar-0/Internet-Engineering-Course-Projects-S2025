package application.result;

import application.exceptions.BaseException;

public record Result<T>(T data, BaseException exception) {

	public Boolean isSuccessful() {
		return exception == null;
	}

	public Boolean isFailure() {
		return exception != null;
	}

	/**
	 * Create a successful result with the given data.
	 * @param data data to be stored in the result
	 * @return a successful result
	 */
	public static <T> Result<T> success(T data) {
		return new Result<>(data, null);
	}

	/**
	 * Create a successful result without any data, it can be used for void return values.
	 * @return a successful result
	 */
	public static <T> Result<T> success() {
		return new Result<>(null, null);
	}

	/**
	 * Create a failed result with the given exception.
	 * @param exception exception to be stored in the result
	 * @return a failed result
	 */
	public static <T> Result<T> failure(BaseException exception) {
		return new Result<>(null, exception);
	}
}
