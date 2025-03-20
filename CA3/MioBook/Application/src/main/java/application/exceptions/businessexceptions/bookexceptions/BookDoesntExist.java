package application.exceptions.businessexceptions.bookexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class BookDoesntExist extends BusinessException {

	private static String message(String title) {
		return "Book with title '" + title + "' does not exist!";
	}

	public BookDoesntExist(String title) {
		super(message(title));
	}
}
