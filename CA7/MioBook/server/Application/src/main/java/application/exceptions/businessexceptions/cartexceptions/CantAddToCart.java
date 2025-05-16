package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantAddToCart extends BusinessException {

	private static String message(String msg) {
		return "Can't add to the cart because: " + msg;
	}

	public CantAddToCart(String msg) {
		super(message(msg));
	}
}
