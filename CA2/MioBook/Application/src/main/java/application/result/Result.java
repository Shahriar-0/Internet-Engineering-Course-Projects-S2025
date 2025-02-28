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

	public static <T> Result<T> success(T data) {
		return new Result<>(data);
	}

	public static <T> Result<T> failure(BaseException exception) {
		return new Result<>(exception);
	}
}
