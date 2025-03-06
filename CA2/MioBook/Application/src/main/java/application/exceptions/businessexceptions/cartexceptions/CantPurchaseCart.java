package application.exceptions.businessexceptions.cartexceptions;

import application.exceptions.businessexceptions.BusinessException;

public class CantPurchaseCart extends BusinessException {

	public CantPurchaseCart(String msg) {
		super(msg);
	}
}
