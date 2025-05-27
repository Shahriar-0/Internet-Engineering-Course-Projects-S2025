package application.exceptions.businessexceptions.userexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class BookIsNotAccessible extends BusinessException {

	private static String message(String title) {
		return "Book '" + title + "' is not accessible!";
	}

	public BookIsNotAccessible(String title) {
		super(message(title));
	}
}
