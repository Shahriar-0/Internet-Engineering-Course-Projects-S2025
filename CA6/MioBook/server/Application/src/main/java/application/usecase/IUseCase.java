package application.usecase;

// Every use case implementing this, should have at least one method called perform
public interface IUseCase {
	UseCaseType getType();
}
