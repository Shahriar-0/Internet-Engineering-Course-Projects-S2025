package webapi.services;

import domain.entities.user.User;
import lombok.Getter;
import org.springframework.stereotype.Service;
import webapi.exceptions.AuthenticationException;

@Service
@Getter
public class AuthenticationService {

	private User user = null;

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
