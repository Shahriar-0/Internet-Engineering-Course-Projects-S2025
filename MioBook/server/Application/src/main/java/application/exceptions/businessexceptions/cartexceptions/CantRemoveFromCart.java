package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantRemoveFromCart extends BusinessException {

	private static String message(String msg) {
		return "Can't remove from the cart because: " + msg;
	}

	public CantRemoveFromCart(String msg) {
		super(message(msg));
	}
}
