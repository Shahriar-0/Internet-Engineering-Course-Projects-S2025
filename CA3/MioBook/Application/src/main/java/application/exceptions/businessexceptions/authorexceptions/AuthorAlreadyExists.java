package application.exceptions.businessexceptions.authorexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class AuthorAlreadyExists extends BusinessException {

	private static String message(String name) {
		return "Author with name '" + name + "' already exists!";
	}

	public AuthorAlreadyExists(String authorName) {
		super(message(authorName));
	}
}
