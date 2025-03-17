package webapi.services;

import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UseCaseService {

	private final List<IUseCase> useCaseList;

	public IUseCase getUseCase(UseCaseType type) {
		for (IUseCase useCase : useCaseList) {
			if (useCase.getType().equals(type))
				return useCase;
		}

		throw new IllegalArgumentException("Unknown Use Case Type!");
	}
}
