package application.validators;

import application.result.Result;

public interface IBaseValidator<T> {
	Result<T> validate(T input);
}
