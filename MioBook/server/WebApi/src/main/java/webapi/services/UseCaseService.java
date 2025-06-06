package webapi.services;

import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UseCaseService {

	private final List<IUseCase> useCaseList;

	@PostConstruct
    public void validateUseCases() {
        for (IUseCase useCase : useCaseList) {
            boolean hasPerformMethod = Arrays.stream(useCase.getClass().getMethods())
                							 .anyMatch(method -> method.getName().equals("perform"));

            if (!hasPerformMethod)
                throw new IllegalStateException(
                    "Use case " + useCase.getClass().getSimpleName() + " does not define a 'perform' method."
                );
        }
    }

	public IUseCase getUseCase(UseCaseType type) {
		for (IUseCase useCase : useCaseList) {
			if (useCase.getType().equals(type))
				return useCase;
		}

		throw new IllegalArgumentException("Unknown Use Case Type!");
	}
}
