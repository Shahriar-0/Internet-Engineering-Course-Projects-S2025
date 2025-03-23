package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantPurchaseCart extends BusinessException {

	private static String message(String msg) {
		return "Can't purchase the cart because: " + msg;
	}

	public CantPurchaseCart(String msg) {
		super(message(msg));
	}
}
