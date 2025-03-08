package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantAddToCart extends BusinessException {

	public CantAddToCart(String msg) {
		super(msg);
	}
}
