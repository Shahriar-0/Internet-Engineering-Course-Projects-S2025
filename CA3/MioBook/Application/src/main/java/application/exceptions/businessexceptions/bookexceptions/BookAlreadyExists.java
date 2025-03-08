package application.exceptions.businessexceptions.bookexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class BookAlreadyExists extends BusinessException {

	private static String message(String title) {
		return "Book with title '" + title + "' already exists!";
	}

	public BookAlreadyExists(String title) {
		super(message(title));
	}
}
