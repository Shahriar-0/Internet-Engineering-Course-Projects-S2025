package application.validators;

import application.response.Response;

public interface IBaseValidator<T> {
    Response<T> validate(T input);
}
