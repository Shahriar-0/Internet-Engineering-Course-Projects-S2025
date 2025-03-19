package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantRemoveFromCart extends BusinessException {

	public CantRemoveFromCart(String msg) {
		super(msg);
	}
}
