package application.exceptions.businessexceptions.authorexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class AuthorDoesNotExists extends BusinessException {

	private static String message(String name) {
		return "Author with username '" + name + "' does not exist!";
	}

	public AuthorDoesNotExists(String authorName) {
		super(message(authorName));
	}
}
