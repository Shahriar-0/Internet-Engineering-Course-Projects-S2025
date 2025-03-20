package webapi.services;

import domain.entities.User;
import org.springframework.stereotype.Service;
import webapi.exceptions.AuthenticationException;

@Service
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

	public User.Role getUserRole() {
		return user.getRole();
	}

	public String getUserName() {
		return user.getUsername();
	}

	public void setLoggedInUser(User user) {
		this.user = user;
	}

	public void unSetLoggedInUser() {
		this.user = null;
	}
}
