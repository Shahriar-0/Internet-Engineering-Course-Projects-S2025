package application.result;

import application.exceptions.BaseException;
import lombok.Getter;

@Getter
public class Result<T> {

	private T data;
	private BaseException exception;

	public Boolean isSuccessful() {
		return exception == null;
	}

	public Boolean isFailure() {
		return exception != null;
	}

	private Result(T data) {
		this.data = data;
	}

	private Result(BaseException exception) {
		this.exception = exception;
	}

	/**
	 * Copy constructor. Copies the data (if any and if possible) and the exception.
	 * @param <U>
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public <U> Result(Result<U> result) {
		if (result.getData() != null) {
			try {
				this.data = (T) result.getData();
			} catch (ClassCastException e) {
				this.data = null;
			}
		}
		this.exception = result.getException();
	}

	/**
	 * Create a successful result with the given data.
	 * @param data data to be stored in the result
	 * @return a successful result
	 */
	public static <T> Result<T> success(T data) {
		return new Result<>(data);
	}

	/**
	 * Create a failed result with the given exception.
	 * @param exception exception to be stored in the result
	 * @return a failed result
	 */
	public static <T> Result<T> failure(BaseException exception) {
		return new Result<>(exception);
	}
}
