package webapi.services;

import application.repositories.IUserRepository;
import domain.entities.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webapi.exceptions.AuthenticationException;

@Getter
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;

	private User user = null;

    public User getUser() {
        return userRepository.findByUsername(user.getUsername()).get();
    }

	public void validateSomeOneLoggedIn() {
		if (user == null)
			throw AuthenticationException.noOneLoggedIn();
	}

	public void validateNoOneLoggedIn() {
		if (user != null)
			throw AuthenticationException.someOneLoggedIn();
	}

	public void setLoggedInUser(User user) {
		this.user = user;
	}

	public void unSetLoggedInUser() {
		this.user = null;
	}
}
